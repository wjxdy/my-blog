<style scoped lang="css">
/* 标签云卡片样式 */
.tags-card {
    background: #fff;

    padding: 20px;
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
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { Loading } from '@element-plus/icons-vue'
import { getTagListApi } from '@/api/tag'
import { getArticlePageApi } from '@/api/article'
import { useUserInfoStore } from '@/stores/counter'
import type { Tag } from '@/types/api'

const route = useRoute()
const userInfoStore = useUserInfoStore()

const tagList = ref<Tag[]>([])
const loading = ref(false)

// 检查标签是否已选中
const isSelected = (tag: Tag) => {
    return userInfoStore.pageInfo.tags.some(t => t.articleTagId === tag.articleTagId)
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
        console.error('加载标签列表失败:', error)
    } finally {
        loading.value = false
    }
}

// 点击标签
const handleTagClick = (tag: Tag) => {
    const index = userInfoStore.pageInfo.tags.findIndex(t => t.articleTagId === tag.articleTagId)
    if (index > -1) {
        userInfoStore.pageInfo.tags.splice(index, 1)
    } else {
        userInfoStore.pageInfo.tags.push(tag)
    }
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
        }
    } catch (error) {
        console.error('查询失败:', error)
    }
}

onMounted(() => {
    loadTagList()
})
</script>
