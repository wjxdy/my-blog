<style scoped>
.blog-container {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
    font-family: Arial, sans-serif;
}

.blog-item {
    padding: 20px;
    background: #fff;
    max-width: 900px;
    max-height: 150px;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    margin-bottom: 15px;
    transition: transform 0.2s;
    /* 增加悬停缩放效果 */
}

/* 鼠标悬停在博客项上时轻微上浮，增强交互感 */
.blog-item:hover {
    transform: translateY(-2px);
}

/* 核心：修改标题链接样式，去除下划线并优化外观 */
.blog-title {
    color: #333;
    margin: 0 0 10px 0;
    cursor: pointer;
    font-size: 1.4rem;
    /* 增大标题字体 */
    font-weight: 600;
    /* 加粗标题 */
    text-decoration: none;
    /* 去除下划线 */
    display: inline-block;
    /* 让margin生效 */
    transition: color 0.2s;
    /* 颜色过渡动画 */
}

.blog-title:hover {
    color: #42b983;
    /* 可以再加一个轻微的放大效果 */
    transform: scale(1.01);
}

.blog-meta {
    color: #666;
    font-size: 14px;
    margin-bottom: 15px;
    display: flex;
    gap: 20px;
}

.blog-content {
    color: #444;
    line-height: 1.6;
    /* 内容超出时显示省略号（配合max-height） */
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    /* 最多显示2行 */
    -webkit-box-orient: vertical;
}

hr {
    border: none;
    border-top: 1px solid #eee;
    margin: 15px 0;
}
.demo-pagination-block{

    margin-left: 210px;

}



</style>
<template>
    <div class="blog-item" v-for="(article, index) in ArticleList" :key="article.articleId">
        <router-link :to="`/article/${article.articleId}`" class="blog-title">
            {{ article.articleTitle }}
        </router-link>

        <!-- 渲染博客元信息（作者、时间） -->
        <div class="blog-meta">
            <span>作者：{{ article.readerUsername }}</span>
            <span>发布时间：{{ article.articleAddTime }}</span>
        </div>

        <!-- 渲染博客内容 -->
        <div class="blog-content">{{ article.articleIntro }}</div>

        <!-- 分割线 -->
        <hr v-if="index !== ArticleList.length - 1"> <!-- 最后一条不显示分割线 -->
    </div>
    <div class="demo-pagination-block">
        <el-pagination v-model:current-page="pageInfo.currentPage" v-model:page-size="pageInfo.pageSize"
            :page-sizes="[5, 10, 20, 50]" size="large" 
            layout="total, sizes, prev, pager, next, jumper" :total="pageInfo.pageTotal" @size-change="handleSizeChange"
            @current-change="handleCurrentChange" />
    </div>

</template>
<script setup lang="ts">
import {  getArticlePageApi } from "@/api/article";
import { ref, onMounted } from "vue";
import { ElMessage } from 'element-plus';
const pageInfo = ref({
    pageSize:5,
    pageTotal:0, 
    currentPage:1
})

const handleSizeChange = (val: number) => {
    pageInfo.value.pageSize = val; // val是Number类型，直接赋值
    pageInfo.value.currentPage = 1; // 切换页大小时，默认回到第一页
    getArticlePage(); // 重新查询
};

// 4. 修复：currentPage变化时的处理（更新页码后重新查询）
const handleCurrentChange = (val: number) => {
    pageInfo.value.currentPage = val; // val是Number类型，直接赋值
    getArticlePage(); // 重新查询
};
const ArticleList = ref([]);

const getArticlePage = async () => {
    const result = await getArticlePageApi(pageInfo.value);

    if (result.code == 200) {
        ArticleList.value = result.data.data
        pageInfo.value.pageTotal = result.data.pageTotal
        console.log(ArticleList.value);

        ElMessage.success("文章列表查询成功")

    } else {

        ElMessage.error("查询失败")
    }
}






onMounted(() => { 

    getArticlePage()


})

</script>