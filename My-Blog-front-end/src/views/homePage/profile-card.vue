<style scoped lang="css">
/* 个人信息卡片样式 - 简约小巧风格 */
.profile-card {
    background: #fff;

    padding: 15px;
    color: #333;
    text-align: center;
}

.profile-avatar {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    border: 1px solid #e9ecef;
    margin-bottom: 8px;
    object-fit: cover;
    cursor: pointer;
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

.profile-github {
    margin-top: 10px;
    padding-top: 10px;
    border-top: 1px solid #f0f0f0;
}

.profile-github a {
    color: #666;
    text-decoration: none;
    font-size: 12px;
}

.profile-github a:hover {
    color: #333;
}

.profile-bio {
    margin-top: 10px;
    padding: 10px;
    font-size: 13px;
    color: #666;
    line-height: 1.5;
    text-align: left;
    background: #f8f9fa;
    border-radius: 6px;
    word-break: break-word;
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
</style>

<template>
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
        <div class="profile-bio" v-if="userInfo?.bio">
            {{ userInfo.bio }}
        </div>
        <div class="profile-github">
            <a href="https://github.com/wjxdy/my-blog" target="_blank" rel="noopener noreferrer">
                GitHub: wjxdy/my-blog
            </a>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { getTagListApi } from '@/api/tag'
import { getUserInfoApi, getPublicUserInfoApi, type UserInfo } from '@/api/user'
import { getArticlePageApi } from '@/api/article'
import type { Tag } from '@/types/api'

const router = useRouter()

const userInfo = ref<UserInfo | null>(null)
const tagList = ref<Tag[]>([])
const articleCount = ref(0)

// 获取用户信息（已登录获取自己的，游客获取博主的）
const loadUserInfo = async () => {
    try {
        const userInfoStr = localStorage.getItem('userInfo')
        const token = userInfoStr ? JSON.parse(userInfoStr).token : null
        
        if (token) {
            const res = await getUserInfoApi()
            if ((res.code == 200 || res.code == '200') && res.data) {
                userInfo.value = res.data
            }
        } else {
            const res = await getPublicUserInfoApi()
            if ((res.code == 200 || res.code == '200') && res.data) {
                userInfo.value = res.data
            }
        }
    } catch (error) {
        console.error('加载用户信息失败:', error)
    }
}

// 获取标签列表
const loadTagList = async () => {
    try {
        const res = await getTagListApi()
        if (res.code == 200 || res.code == '200') {
            tagList.value = res.data || []
        }
    } catch (error) {
        console.error('加载标签列表失败:', error)
    }
}

// 获取文章数量
const loadArticleCount = async () => {
    try {
        const res = await getArticlePageApi({
            pageSize: 1,
            pageTotal: 0,
            currentPage: 1,
            condition: '',
            tags: []
        })
        if (res.code == 200 || res.code == '200') {
            articleCount.value = res.data.pageTotal || 0
        }
    } catch (error) {
        console.error('加载文章数量失败:', error)
    }
}

const goToSettings = () => {
    router.push('/settings')
}

onMounted(() => {
    loadUserInfo()
    loadTagList()
    loadArticleCount()
})
</script>
