<style scoped>
/* 整体布局样式 */
.parent {
    display: flex;
    min-height: auto;
    width: 100%;
    max-width: 1400px;
    margin: 10px auto ;
    padding: 0 20px;
    box-sizing: border-box;
    gap: 30px;
    align-items: flex-start;
    /* 使子项根据自身内容高度显示，不拉伸 */
}
/* 左侧整体容器：控制内部两个侧边栏上下排列 */
.left-container {
    display: flex;
    flex-direction: column;
    /* 垂直排列 */
    gap: 20px;
    /* 两个侧边栏之间的上下间距 */
    width: 300px;
    /* 左侧整体宽度（和单个侧边栏一致） */
    flex-shrink: 0;
    /* 不压缩 */
}

.p-side{
    width: 300px;
    background-color: rgba(255, 255, 255, 0.7);
    flex-shrink: 0;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    padding: 10px;
}

/* 左侧边栏样式 */
.l-side {
    width: 300px;
    background-color: rgba(255, 255, 255, 0.7);
    flex-shrink: 0;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    padding: 10px;
    
}

/* 中间内容区域样式 */
.middle {
    flex-grow: 1;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.5);
    padding: 10px 20px;
}

/* 右侧边栏样式 */
.r-side {
    width: 300px;
    background-color: rgba(255, 255, 255);
    flex-shrink: 0;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    padding: 10px;
    padding-bottom: 10px
}

.blog-item {
    padding: 20px;
    background: #fff;
    max-width: 100%;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.08);
    margin: 0 auto 15px;
    /* 关键：左右auto实现居中，保留底部margin */
    transition: transform 0.2s;
}

/* 博客文章悬停效果 */
.blog-item:hover {
    transform: translateY(-4px);
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
    border-color: rgba(66, 185, 131, 0.2);
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

/* 博客标题悬停效果 */
.blog-title:hover {
    color: #42b983;
    transform: translateX(5px);
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

/* 博客内容摘要样式 */
.blog-content {
    color: #495057;
    line-height: 1.8;
    font-size: 1rem;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    text-align: justify;
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
            <div class="p-side">
                <price-chart-view />
            </div>
            <div class="l-side">
                <l-side-view />

            </div>

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

                <!-- 渲染博客内容 -->
                <div class="blog-content">{{ article.articleIntro }}</div>


            </div>
            <div class="demo-pagination-block">
                <el-pagination v-model:current-page="userInfoStore.pageInfo.currentPage" v-model:page-size="userInfoStore.pageInfo.pageSize"
                    :page-sizes="[5, 10, 20, 50]" size="large" layout="total, sizes, prev, pager, next, jumper"
                    :total="userInfoStore.pageInfo.pageTotal" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
            </div>
        </div>
        <div class="r-side">
            <r-side-view />
        </div>
    </div>
</template>
<script setup lang="ts">
import { getArticlePageApi } from "@/api/article";
import { ref, onMounted } from "vue";
import { ElMessage } from 'element-plus';
import RSideView from './r-side.vue'
import LSideView from './l-side.vue'
import priceChartView from './priceChart.vue'
import { formatDate } from "@/utils/dateUtils";
import { useUserInfoStore } from "@/stores/counter";
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