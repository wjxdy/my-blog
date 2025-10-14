<!-- src/pages/ArticlePage.vue -->
<template>
    <div class="article-container" v-if="article">
        <!-- 文章标题 -->
        <h1 class="article-title">{{ article.articleTitle }}</h1>
        <!-- 文章元信息 -->
        <div class="article-meta">
            <span style="margin-right: 15px;">作者：{{ article.readerUsername }}</span>
            <span>发布时间：{{ article.articleAddTime }}</span>
        </div>
        <!-- 文章完整内容（核心区域） -->
        <div >
            <MdPreview :editorId="id" :modelValue="article.articleContext" />
            <MdCatalog :editorId="id" :scrollElement="scrollElement" />
        </div>
        <!-- 返回按钮 -->

    </div>
    <div v-else>加载失败...</div>
</template>

<script lang="ts" setup>
import { ref, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage } from 'element-plus';

import { MdPreview, MdCatalog } from 'md-editor-v3';
import 'md-editor-v3/lib/preview.css';
import { getArticleApi } from "@/api/article";
const id = 'preview-only';
const scrollElement = document.documentElement;
// 获取路由参数（文章ID）
const route = useRoute()
const articleId = route.params.id // 从URL中获取文章ID（如 /article/1 中的 1）

// 存储文章完整数据
const article = ref({
    articleId: "",
    readerUsername: "",
    articleTitle: " ",
    articleIntro: " ",
    articleAddTime: " ",
    articleContext: "  ",
    articleGoodNumber: null ,
    articleLookNumber:null ,
    articleCollectionNumber:null

} )

// 模拟获取文章完整内容（实际项目中调用后端接口）
const getArticleDetail = async () => {
    // 实际项目中替换为：await axios.get(`/api/articles/${id}`)
    const result = await getArticleApi(articleId)

    if (result.code == 200) {
        article.value = result.data
        console.log(article.value);

        ElMessage.success("查询成功")

    } else {

        ElMessage.error("查询失败")
    }
}


// 页面加载时获取文章详情
onMounted(() => {
    getArticleDetail()
})
</script>

<style scoped>
.article-container {
    max-width: 800px;
    margin: 0 auto 0 289px;
    padding: 20px;
}

.article-title {
    color: #2c3e50;
    margin: 20px 0;
}

.article-meta {
    color: #666;
    margin: 10px 0 20px;
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;
}

.article-content {
    line-height: 1.8;
    color: #333;
}

.article-content p {
    margin: 15px 0;
}

/* 段落间距 */
.back-btn {
    margin-top: 30px;
    padding: 8px 16px;
    cursor: pointer;
}
</style>