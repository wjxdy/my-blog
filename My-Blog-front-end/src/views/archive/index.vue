<style scoped lang="css">
.archive-page {
    max-width: 1200px;
    margin: 0 auto;
    padding: 40px 20px;
    min-height: calc(100vh - 200px);
    background: rgba(255, 255, 255, 0.7);
}

.archive-header {
    text-align: center;
    margin-bottom: 60px;
    padding-bottom: 30px;
    border-bottom: 1px solid #e9ecef;
}

.archive-header h1 {
    font-size: 32px;
    font-weight: 600;
    color: #1a1a1a;
    margin-bottom: 10px;
}

.archive-header p {
    color: #666;
    font-size: 16px;
}

.archive-stats {
    display: flex;
    justify-content: center;
    gap: 40px;
    margin-top: 20px;
}

.stat-item {
    text-align: center;
}

.stat-number {
    font-size: 28px;
    font-weight: 600;
    color: #1a1a1a;
}

.stat-label {
    font-size: 14px;
    color: #999;
    margin-top: 4px;
}

.timeline-container {
    position: relative;
}

.timeline-container::before {
    content: '';
    position: absolute;
    left: 0;
    top: 0;
    bottom: 0;
    width: 2px;
    background: #e9ecef;
}

.year-section {
    position: relative;
    padding-left: 30px;
    margin-bottom: 40px;
}

.year-section::before {
    content: '';
    position: absolute;
    left: -5px;
    top: 5px;
    width: 12px;
    height: 12px;
    background: #fff;
    border: 2px solid #999;
    border-radius: 50%;
}

.year-title {
    font-size: 24px;
    font-weight: 600;
    color: #1a1a1a;
    margin-bottom: 20px;
    cursor: pointer;
    display: flex;
    align-items: center;
    gap: 10px;
}

.year-title:hover {
    color: #666;
}

.month-section {
    margin-bottom: 30px;
    padding-left: 20px;
}

.month-title {
    font-size: 18px;
    font-weight: 500;
    color: #333;
    margin-bottom: 15px;
    padding-bottom: 10px;
    border-bottom: 1px solid #f0f0f0;
}

.article-list {
    display: flex;
    flex-direction: column;
    gap: 12px;
}

.article-item {
    display: flex;
    align-items: baseline;
    padding: 12px 16px;
    background: #fafafa;
    border-radius: 6px;
    transition: all 0.2s;
    cursor: pointer;
}

.article-item:hover {
    background: #f0f0f0;
}

.article-date {
    font-size: 14px;
    color: #999;
    min-width: 50px;
    margin-right: 20px;
    font-family: 'Monaco', monospace;
}

.article-title {
    flex: 1;
    font-size: 15px;
    color: #333;
    line-height: 1.5;
}

.article-item:hover .article-title {
    color: #000;
}

.loading-container {
    display: flex;
    justify-content: center;
    padding: 60px 0;
}

.empty-state {
    text-align: center;
    padding: 80px 0;
    color: #999;
}

.empty-state .el-icon {
    font-size: 64px;
    margin-bottom: 20px;
    color: #ddd;
}

@media (max-width: 768px) {
    .archive-page {
        padding: 20px 15px;
    }
    
    .archive-header h1 {
        font-size: 24px;
    }
    
    .timeline-container::before {
        left: 0;
    }
    
    .year-section {
        padding-left: 20px;
    }
    
    .article-date {
        min-width: 40px;
        margin-right: 10px;
        font-size: 12px;
    }
    
    .article-title {
        font-size: 14px;
    }
}
</style>

<template>
    <div class="archive-page">
        <div class="archive-header">
            <h1>文章归档</h1>
            <p>按时间顺序整理的所有文章</p>
            <div class="archive-stats">
                <div class="stat-item">
                    <div class="stat-number">{{ totalArticles }}</div>
                    <div class="stat-label">文章总数</div>
                </div>
                <div class="stat-item">
                    <div class="stat-number">{{ totalYears }}</div>
                    <div class="stat-label">年份跨度</div>
                </div>
            </div>
        </div>

        <div v-if="loading" class="loading-container">
            <el-icon size="32" class="is-loading"><Loading /></el-icon>
        </div>

        <div v-else-if="Object.keys(archiveData).length === 0" class="empty-state">
            <el-icon><Document /></el-icon>
            <p>暂无文章</p>
        </div>

        <div v-else class="timeline-container">
            <div v-for="year in sortedYears" :key="year" class="year-section">
                <div class="year-title" @click="toggleYear(year)">
                    {{ year }}
                    <span style="font-size: 14px; color: #999; font-weight: normal;">
                        ({{ getYearCount(year) }} 篇)
                    </span>
                </div>
                
                <div v-show="expandedYears[year] !== false">
                    <div v-for="month in sortedMonths(year)" :key="`${year}-${month}`" class="month-section">
                        <div class="month-title">{{ month }}月</div>
                        <div class="article-list">
                            <div 
                                v-for="article in archiveData[year][month]" 
                                :key="article.articleId"
                                class="article-item"
                                @click="goToArticle(article.articleId)"
                            >
                                <span class="article-date">{{ formatDay(article.articleAddTime) }}</span>
                                <span class="article-title">{{ article.articleTitle }}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRouter } from 'vue-router'
import { Loading, Document } from '@element-plus/icons-vue'
import { getArticlePageApi } from '@/api/article'
import { ElMessage } from 'element-plus'

const router = useRouter()

interface Article {
    articleId: string
    articleTitle: string
    articleAddTime: string
}

const archiveData = ref<Record<string, Record<string, Article[]>>>({})
const loading = ref(false)
const expandedYears = ref<Record<string, boolean>>({})

// 计算总数
const totalArticles = computed(() => {
    let count = 0
    Object.values(archiveData.value).forEach(year => {
        Object.values(year).forEach(month => {
            count += month.length
        })
    })
    return count
})

const totalYears = computed(() => Object.keys(archiveData.value).length)

// 排序后的年份
const sortedYears = computed(() => {
    return Object.keys(archiveData.value).sort((a, b) => parseInt(b) - parseInt(a))
})

// 排序后的月份
const sortedMonths = (year: string) => {
    return Object.keys(archiveData.value[year] || {}).sort((a, b) => parseInt(b) - parseInt(a))
}

// 获取某年的文章数
const getYearCount = (year: string) => {
    let count = 0
    Object.values(archiveData.value[year] || {}).forEach(month => {
        count += month.length
    })
    return count
}

// 格式化日期（只显示日）
const formatDay = (dateStr: string) => {
    const date = new Date(dateStr)
    return `${date.getDate()}`.padStart(2, '0')
}

// 切换年份展开/收起
const toggleYear = (year: string) => {
    expandedYears.value[year] = !expandedYears.value[year]
}

// 跳转到文章
const goToArticle = (articleId: string) => {
    router.push(`/article/${articleId}`)
}

// 加载文章
const loadArticles = async () => {
    loading.value = true
    try {
        const res = await getArticlePageApi({
            pageSize: 9999,
            pageTotal: 0,
            currentPage: 1,
            condition: '',
            tags: []
        })
        
        if ((res.code == 200 || res.code == '200') && res.data.data) {
            const grouped: Record<string, Record<string, Article[]>> = {}
            
            res.data.data.forEach((article: Article) => {
                const date = new Date(article.articleAddTime)
                const year = date.getFullYear().toString()
                const month = (date.getMonth() + 1).toString()
                
                if (!grouped[year]) {
                    grouped[year] = {}
                }
                if (!grouped[year][month]) {
                    grouped[year][month] = []
                }
                grouped[year][month].push(article)
            })
            
            archiveData.value = grouped
            
            // 默认展开所有年份
            Object.keys(grouped).forEach(year => {
                expandedYears.value[year] = true
            })
        }
    } catch (error) {
        console.error('加载文章归档失败:', error)
        ElMessage.error('加载文章归档失败')
    } finally {
        loading.value = false
    }
}

onMounted(() => {
    loadArticles()
})
</script>
