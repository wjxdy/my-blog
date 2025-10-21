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
        <div>
            <MdPreview :editorId="id" :modelValue="article.articleContext" />
            <MdCatalog :editorId="id" :scrollElement="scrollElement" />
        </div>
        <!-- 更多按钮 -->
        <div class="divider">
            <div class="other">
                <el-popover placement="bottom" title="" width="200" trigger="hover">
                    <!-- 弹出框内容 -->
                    <div>
                        <el-row>
                            <el-col :span="12" align="center">
                                <el-tooltip content="文章编辑" placement="bottom" effect="light">
                                    <el-button size="large" @click="goToArticleEdit()" circle>
                                        <el-icon>
                                            <EditPen />
                                        </el-icon>
                                    </el-button>
                                </el-tooltip>
                            </el-col>
                            <el-col :span="12" align="center">
                                <el-tooltip content="删除文章" placement="bottom" effect="light">
                                    <el-button size="large" @click="deleteArticleById()" circle>
                                        <el-icon>
                                            <Delete />
                                        </el-icon>
                                    </el-button>
                                </el-tooltip>
                            </el-col>
                        </el-row>
                    </div>
                    <template #reference>
                        <el-button size="large" circle>
                            <el-icon>
                                <More />
                            </el-icon>
                        </el-button>
                    </template>
                </el-popover>
            </div>
        </div>
    </div>
    <div v-else>加载失败...</div>


</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus';

import { MdPreview, MdCatalog } from 'md-editor-v3';
import 'md-editor-v3/lib/preview.css';
import { getArticleApi,deleteArticleApi } from "@/api/article";
const id = 'preview-only';
const scrollElement = document.documentElement;
// 获取路由参数（文章ID）
const route = useRoute()
const router = useRouter()

const articleId = route.params.id // 从URL中获取文章ID（如 /article/1 中的 1


// 存储文章完整数据
const article = ref({
    articleId: "",
    username: "",
    articleTitle: "",
    articleIntro: "",
    articleAddTime: "",
    articleContext: "",
    articleGoodNumber: null,
    articleLookNumber: null,
    articleCollectionNumber: null

})

const goToArticleEdit = () => {

    router.push({
        name: 'ArticleEdit',

        params: {
            articleId: article.value.articleId
        }
    })


}


// 模拟获取文章完整内容（实际项目中调用后端接口）
const getArticleDetail = async () => {
    // 实际项目中替换为：await axios.get(`/api/articles/${id}`)
    const result = await getArticleApi(articleId as string)

    if (result.code  == 200) {
        article.value = result.data
        console.log(article.value);


    } else {

        ElMessage.error("查询失败")
    }
}
const deleteArticleById = async () => {
    // 实际项目中替换为：await axios.get(`/api/articles/${id}`)
    const result = await deleteArticleApi(articleId as string)

    if (result.code == 200) {
        
        ElMessage.success("删除成功")
        router.push('/homePage')

    } else {

        ElMessage.error("删除失败")
    }
}


// 页面加载时获取文章详情
onMounted(() => {
    getArticleDetail()
})
</script>

<style scoped>
.other{
    display: flex;
    justify-content: flex-end;
    /* 水平方向靠右对齐 */
}
/* 或者顶部边框 */
.divider {
    margin-top: 60px;
    border-top: 1px solid #f0f0f0;
    /* 更浅的灰色 */
    padding-top: 16px;
    /* 顶部留出间距 */
}
.el-main{

    max-width: 800px;
    margin-left: 0px;

}

.article-container {
    max-width: 800px;
    /* 保持最大宽度限制 */
    margin: 0 auto;
    /* 关键：左右自动外边距实现水平居中 */
    padding: 20px;
    /* 保留内边距 */
    /* 可选项：添加上下外边距控制与其他元素的间距 */
    margin-top: 20px;
    margin-bottom: 40px;
}

.article-title {
    text-align: center;
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