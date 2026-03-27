<style scoped lang="css">
.sidebar-container {
    display: flex;
    flex-direction: column;
    gap: 20px;
    position: sticky;
    top: 20px;
}

/* 个人信息卡片样式 - 简约小巧风格 */
.profile-card {
    background: #fff;
    border-radius: 10px;
    padding: 15px;
    color: #333;
    text-align: center;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.profile-avatar {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    border: 1px solid #e9ecef;
    margin-bottom: 8px;
    object-fit: cover;
    cursor: pointer;
    transition: border-color 0.3s;
}

.profile-avatar:hover {
    border-color: #42b983;
}

.profile-avatar-default {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    background: #fff;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 20px;
    font-weight: 600;
    margin: 0 auto 8px;
    border: 1px solid #e0e0e0;
    color: #999;
    cursor: pointer;
    transition: all 0.3s;
}

.profile-avatar-default:hover {
    border-color: #42b983;
    color: #42b983;
}

.profile-name {
    font-size: 14px;
    font-weight: 600;
    margin-bottom: 4px;
    color: #2c3e50;
}

.profile-username {
    font-size: 12px;
    color: #999;
    margin-bottom: 10px;
}

.profile-stats {
    display: flex;
    justify-content: center;
    gap: 20px;
    margin-top: 10px;
    padding-top: 10px;
    border-top: 1px solid #f0f0f0;
}

.stat-item {
    text-align: center;
}

.stat-value {
    font-size: 16px;
    font-weight: 600;
    display: block;
    color: #2c3e50;
}

.stat-label {
    font-size: 11px;
    color: #999;
}

/* 标签云卡片样式 */
.tags-card {
    background: #fff;
    border-radius: 12px;
    padding: 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.tags-title {
    font-size: 16px;
    font-weight: 600;
    color: #2c3e50;
    margin-bottom: 15px;
    padding-bottom: 10px;
    border-bottom: 1px solid #e9ecef;
}

.tags-cloud {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
}

.tag-item {
    cursor: pointer;
    transition: all 0.2s ease;
}

.tag-item:hover {
    transform: translateY(-2px);
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.empty-tags {
    text-align: center;
    color: #999;
    padding: 20px 0;
    font-size: 14px;
}

.loading-container {
    display: flex;
    justify-content: center;
    padding: 20px;
}
</style>

<template>
    <div class="sidebar-container">
        <!-- 个人信息卡片 -->
        <div class="profile-card">
            <img 
                v-if="userInfo?.imgUrl" 
                :src="userInfo.imgUrl" 
                class="profile-avatar" 
                alt="头像"
                @click="goToSettings"
            />
            <div v-else class="profile-avatar-default" @click="goToSettings">
                {{ userInfo?.name ? userInfo.name.charAt(0).toUpperCase() : 'U' }}
            </div>
            <div class="profile-name">{{ userInfo?.name || '访客' }}</div>
            <div class="profile-username">@{{ userInfo?.username || '-' }}</div>
            <div class="profile-stats">
                <div class="stat-item">
                    <span class="stat-value">{{ articleCount }}</span>
                    <span class="stat-label">文章</span>
                </div>
                <div class="stat-item">
                    <span class="stat-value">{{ tagList.length }}</span>
                    <span class="stat-label">标签</span>
                </div>
            </div>
        </div>

        <!-- 标签云卡片 -->
        <div class="tags-card">
            <div class="tags-title">🏷️ 标签云</div>
            <div v-if="loading" class="loading-container">
                <el-icon size="24" class="is-loading"><Loading /></el-icon>
            </div>
            <div v-else-if="tagList.length > 0" class="tags-cloud">
                <el-tag
                    v-for="tag in tagList"
                    :key="tag.articleTagId"
                    class="tag-item"
                    :type="isSelected(tag) ? 'success' : 'info'"
                    effect="light"
                    size="large"
                    round
                    @click="handleTagClick(tag)"
                >
                    {{ tag.articleTagName }}
                </el-tag>
            </div>
            <div v-else class="empty-tags">
                暂无标签
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Loading } from '@element-plus/icons-vue'
import { getTagListApi } from '@/api/tag'
import { getUserInfoApi, getPublicUserInfoApi, type UserInfo } from '@/api/user'
import { getArticlePageApi } from '@/api/article'
import { useUserInfoStore } from '@/stores/counter'
import type { Tag } from '@/types/api'

const router = useRouter()
const route = useRoute()
const userInfoStore = useUserInfoStore()

const userInfo = ref<UserInfo | null>(null)
const tagList = ref<Tag[]>([])
const loading = ref(false)
const articleCount = ref(0)

// 检查标签是否已选中
const isSelected = (tag: Tag) => {
    return userInfoStore.pageInfo.tags.some(t => t.articleTagId === tag.articleTagId)
}

// 获取用户信息（已登录获取自己的，游客获取博主的）
const loadUserInfo = async () => {
    try {
        // 检查是否有token判断是否登录
        const userInfoStr = localStorage.getItem('userInfo')
        const token = userInfoStr ? JSON.parse(userInfoStr).token : null
        
        if (token) {
            // 已登录，获取自己的信息
            const res = await getUserInfoApi()
            if ((res.code == 200 || res.code == '200') && res.data) {
                userInfo.value = res.data
                console.log('已登录，加载用户信息, imgUrl:', res.data?.imgUrl)
            }
        } else {
            // 游客访问，获取博主信息
            const res = await getPublicUserInfoApi()
            if ((res.code == 200 || res.code == '200') && res.data) {
                userInfo.value = res.data
                console.log('游客访问，加载博主信息, imgUrl:', res.data?.imgUrl)
            }
        }
    } catch (error) {
        console.error('获取用户信息失败:', error)
    }
}

// 获取标签列表
const loadTagList = async () => {
    loading.value = true
    try {
        const res = await getTagListApi()
        if (res.code == 200 || res.code == '200') {
            tagList.value = res.data || []
        }
    } catch (error) {
        console.error('获取标签列表失败:', error)
    } finally {
        loading.value = false
    }
}

// 获取文章总数
const loadArticleCount = async () => {
    try {
        const params = {
            ...userInfoStore.pageInfo,
            pageSize: 1,
            currentPage: 1
        }
        const res = await getArticlePageApi(params)
        if (res.code == 200 || res.code == '200') {
            articleCount.value = res.data.pageTotal || 0
        }
    } catch (error) {
        console.error('获取文章数量失败:', error)
    }
}

// 点击标签
const handleTagClick = (tag: Tag) => {
    const index = userInfoStore.pageInfo.tags.findIndex(t => t.articleTagId === tag.articleTagId)
    if (index > -1) {
        // 已选中，取消选择
        userInfoStore.pageInfo.tags.splice(index, 1)
    } else {
        // 未选中，添加选择
        userInfoStore.pageInfo.tags.push(tag)
    }
    // 触发搜索
    userInfoStore.pageInfo.currentPage = 1
    getArticlePage()
}

// 获取文章列表
const getArticlePage = async () => {
    try {
        const result = await getArticlePageApi(userInfoStore.pageInfo)
        if (result.code == 200 || result.code == '200') {
            userInfoStore.ArticleList = result.data.data
            userInfoStore.pageInfo.pageTotal = result.data.pageTotal
            // 显示提示
            if (userInfoStore.pageInfo.tags.length > 0) {
                const tagNames = userInfoStore.pageInfo.tags.map(t => t.articleTagName).join(', ')
                ElMessage.success(`已筛选标签: ${tagNames}`)
            }
        }
    } catch (error) {
        console.error('Error fetching articles:', error)
        ElMessage.error('查询失败')
    }
}

// 跳转到设置页面
const goToSettings = () => {
    router.push('/settings')
}

// 监听路由变化，返回首页时刷新用户信息
watch(() => route.path, (newPath) => {
    if (newPath === '/homePage' || newPath === '/') {
        loadUserInfo()
        loadArticleCount()
    }
})

onMounted(() => {
    loadUserInfo()
    loadTagList()
    loadArticleCount()
})
</script>
