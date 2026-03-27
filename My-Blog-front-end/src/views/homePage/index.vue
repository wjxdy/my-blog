<style scoped>
/* 整体布局样式 */
.parent {
    display: flex;
    min-height: auto;
    width: 100%;
    max-width: 1500px;
    margin: 0 auto;
    padding: 0 20px;
    box-sizing: border-box;
    gap: 30px;
    align-items: flex-start;
    /* 使子项根据自身内容高度显示，不拉伸 */
}
/* 左侧整体容器 */
.left-container {
    display: flex;
    flex-direction: column;
    width: 260px;
    flex-shrink: 0;
    position: sticky;
    top: 20px;
    align-self: flex-start;
}

/* 中间内容区域样式 */
.middle {
    flex-grow: 1;
    padding: 10px 20px;
    background-color: #fff;
}

/* 右侧边栏样式 */
.r-side {
    width: 260px;
    background-color: #fff;
    flex-shrink: 0;
    position: sticky;
    top: 20px;
    align-self: flex-start;
}

/* ==================== 手机端响应式设计 ==================== */
@media screen and (max-width: 1024px) {
    .parent {
        flex-direction: column;
        padding: 0 10px;
        gap: 15px;
    }
    
    /* 左侧边栏隐藏 */
    .left-container {
        display: none;
    }
    
    /* 右侧边栏放到底部 */
    .r-side {
        width: 100%;
        order: 3;
    }
    
    /* 中间内容全宽 */
    .middle {
        width: 100%;
        padding: 10px;
    }
}

@media screen and (max-width: 768px) {
    .parent {
        padding: 0 8px;
        gap: 10px;
        margin: 5px auto;
    }
    
    /* 隐藏左右边栏，只保留中间内容 */
    .left-container,
    .r-side {
        display: none;
    }
    
    /* 中间内容全宽显示 */
    .middle {
        width: 100%;
        padding: 8px;
        border-radius: 8px;
    }
    
    /* 博客列表样式调整 */
    .blog-item {
        padding: 12px;
        margin-bottom: 10px;
        border-radius: 6px;
    }
    
    .blog-title {
        font-size: 1.1rem;
        margin-bottom: 10px;
    }
    
    .blog-meta {
        font-size: 0.8rem;
        gap: 10px;
        margin-bottom: 10px;
    }
    
    /* 预览内容高度调整 */
    .blog-content-preview :deep(.md-preview-wrapper) {
        max-height: 150px;
    }
    
    /* 分页器调整 */
    .demo-pagination-block {
        padding: 10px 0;
    }
    
    :deep(.el-pagination) {
        justify-content: center;
        flex-wrap: wrap;
    }
}

.blog-item {
    padding: 20px;
    background: #fff;
    max-width: 100%;
    border-radius: 8px;
    border: 1px solid #e9ecef;
    margin: 0 auto 15px;
    /* 关键：左右auto实现居中，保留底部margin */
    transition: transform 0.2s;
}



/* 博客标题样式 */
.blog-title {
    color: #2c3e50;
    margin: 0 0 15px 0;
    cursor: pointer;
    font-size: 1.5rem;
    font-weight: 700;
    text-decoration: none;
    display: inline-block;
    transition: all 0.3s ease;
    line-height: 1.4;
}



/* 博客元信息样式 */
.blog-meta {
    color: #6c757d;
    font-size: 0.95rem;
    margin-bottom: 20px;
    display: flex;
    gap: 25px;
    align-items: center;
}

/* 博客内容预览样式 */
.blog-content-preview {
    position: relative;
}

.blog-content-preview :deep(.md-preview) {
    background: transparent !important;
}

/* 缩小预览内容的字体大小 */
.blog-content-preview :deep(.md-preview) {
    font-size: 14px;
    line-height: 1.6;
}

/* 限制预览区域的最大高度 */
.blog-content-preview :deep(.md-preview-wrapper) {
    max-height: 120px;
    overflow: hidden;
    position: relative;
}

/* 底部渐变遮罩，提示更多内容 */
.blog-content-preview :deep(.md-preview-wrapper)::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    height: 60px;
    background: linear-gradient(to bottom, transparent, #fff);
    pointer-events: none;
}

/* 阅读全文链接样式 - 灰色极简风格 */
.read-more {
    text-align: left;
    padding: 10px 0 0;
}

.read-more a {
    color: #999;
    text-decoration: none;
    font-size: 13px;
    font-weight: 400;
}

.read-more a:hover {
    color: #666;
}

/* 分割线样式 */
hr {
    border: none;
    border-top: 1px solid #e9ecef;
    margin: 15px 0;
}






</style>
<template>
    <div class="parent">
        <div class="left-container">
            <profile-card />
            <price-chart-view />
        </div>
        
        <div class="middle">
            <div class="blog-item" v-for="(article, index) in useUserInfoStore().ArticleList" :key="article.articleId">
                <router-link :to="`/article/${article.articleId}`" class="blog-title">
                    {{ article.articleTitle }}
                </router-link>

                <!-- 渲染博客元信息（作者、时间） -->
                <div class="blog-meta">
                    <span>作者：{{ article.username }}</span>
                    <span>发布时间：{{ formatDate(article.articleAddTime) }}</span>
                </div>

                <!-- 渲染博客内容摘要 -->
                <div class="blog-content-preview">
                    <MdPreview 
                        :editorId="'preview-' + article.articleId" 
                        :modelValue="getPreviewContent(article.articleContext)" 
                    />
                    <div class="read-more">
                        <router-link :to="`/article/${article.articleId}`">阅读全文 →</router-link>
                    </div>
                </div>


            </div>
            <div class="demo-pagination-block">
                <el-pagination v-model:current-page="userInfoStore.pageInfo.currentPage" v-model:page-size="userInfoStore.pageInfo.pageSize"
                    :page-sizes="[5, 10, 20, 50]" size="large" layout="total, sizes, prev, pager, next, jumper"
                    :total="userInfoStore.pageInfo.pageTotal" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
            </div>
        </div>
        <div class="r-side">
            <tags-cloud />
        </div>
    </div>
</template>
<script setup lang="ts">
import { getArticlePageApi } from "@/api/article";
import { ref, onMounted } from "vue";
import { ElMessage } from 'element-plus';
import ProfileCard from './profile-card.vue'
import TagsCloud from './tags-cloud.vue'
import priceChartView from './priceChart.vue'
import { formatDate } from "@/utils/dateUtils";
import { useUserInfoStore } from "@/stores/counter";
import { MdPreview } from 'md-editor-v3';
import 'md-editor-v3/lib/preview.css';
const userInfoStore = useUserInfoStore();
    
const handleSizeChange = (val: number) => {
    userInfoStore.pageInfo.pageSize = val;
    userInfoStore.pageInfo.currentPage = 1;
    getArticlePage();
};

const handleCurrentChange = (val: number) => {
    userInfoStore.pageInfo.currentPage = val;
    getArticlePage();
};

// 获取预览内容（截取前300字符的Markdown内容）
const getPreviewContent = (content: string | undefined) => {
    if (!content) return ''
    // 截取前300字符，保持简洁
    return content.slice(0, 300) + (content.length > 300 ? '...' : '')
}

const getArticlePage = async () => {
    try {
        
        const result = await getArticlePageApi(useUserInfoStore().pageInfo);

        if (result.code == 200) {
            userInfoStore.ArticleList = result.data.data;
            userInfoStore.pageInfo.pageTotal = result.data.pageTotal;
        } else {
            ElMessage.error("查询失败");
        }
    } catch (error) {
        console.error('Error fetching articles:', error);
        ElMessage.error("网络错误，请稍后重试");
    }
}

onMounted(() => {
    getArticlePage();
});

</script>