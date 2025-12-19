<!-- src/pages/ArticlePage.vue -->
<template>
    <!-- 外层容器，包含主内容区和侧边栏目录 -->
    <div class="page-wrapper">
        <!-- 主内容区，包含文章和标题 -->
        <div class="article-container" v-if="article">
            <!-- 文章标题 -->
            <h1 class="article-title">{{ article.articleTitle }}</h1>
            <!-- 文章元信息 -->
            <div class="article-meta">
                <span style="margin-right: 15px;">作者：{{ article.username }}</span>
                <span>发布时间：{{ formatDate(article.articleAddTime) }}</span>
            </div>
            <!-- 文章完整内容（核心区域） -->
            <div class="article-content">
                <MdPreview :editorId="id" :modelValue="article.articleContext" />
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

        <!-- 右侧固定目录 -->
        <div class="fixed-catalog" v-if="article">
            <MdCatalog :editorId="id" :scrollElement="scrollElement" />
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus';

import { MdPreview, MdCatalog } from 'md-editor-v3';
import 'md-editor-v3/lib/preview.css';
import { getArticleApi, deleteArticleApi } from "@/api/article";
import { formatDate } from "@/utils/dateUtils";

const id = 'preview-only';
const scrollElement = document.documentElement;
// 获取路由参数（文章ID）
const route = useRoute()
const router = useRouter()

const articleId = route.params.id // 从URL中获取文章ID（如 /article/1 中的 1

// 定义文章类型
interface Article {
    articleId: string;
    username: string;
    articleTitle: string;
    articleIntro: string;
    articleAddTime: string;
    articleContext: string;
    articleGoodNumber: number | null;
    articleLookNumber: number | null;
    articleCollectionNumber: number | null;
}

// 存储文章完整数据
const article = ref<Article>({
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

    if (result.code == 200) {
        article.value = result.data
    } else {
        ElMessage.error("查询失败")
    }
}

const deleteArticleById = async () => {
    try {
        await ElMessageBox.confirm(
            '确定要删除这篇文章吗？此操作不可撤销！',
            '删除确认',
            {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }
        )

        // 用户确认后执行删除操作
        const result = await deleteArticleApi(articleId as string)

        if (result.code == 200) {
            ElMessage.success("删除成功")
            router.push('/homePage')
        } else {
            ElMessage.error("删除失败")
        }
    } catch (error) {
        // 用户取消删除操作
        ElMessage.info("已取消删除")
    }
}

// 页面加载时获取文章详情
onMounted(() => {
    getArticleDetail()
})
</script>

<style scoped>
/* 整体样式优化 */
.other {
    display: flex;
    justify-content: flex-end;
    /* 水平方向靠右对齐 */
}

/* 分隔线样式 */
.divider {
    margin-top: 60px;
    border-top: 1px solid #e9ecef;
    /* 更柔和的灰色 */
    padding-top: 20px;
    /* 顶部留出间距 */
}

/* 页面整体布局 */
.page-wrapper {
    position: relative;
    max-width: 1400px;
    margin: 0 auto;
    padding: 0 20px;
}

/* 文章容器卡片式设计 */
.article-container {
    max-width: 65%;
    /* 适当的最大宽度，适合阅读 */
    margin: 0 auto;
    /* 关键：左右自动外边距实现水平居中 */
    padding: 40px;
    /* 增加内边距，提升阅读体验 */
    margin-bottom: 50px;
    box-sizing: border-box;
    /* 确保padding不会影响总宽度 */
    background-color: #ffffff;
    /* 白色背景，提升对比度 */
    border-radius: 12px;
    /* 圆角边框，增加现代感 */
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    /* 柔和阴影，增加层次感 */
}

/* 标题样式优化 */
.article-title {
    text-align: center;
    color: #2c3e50;
    /* 深色标题，提升可读性 */
    margin: 0 0 30px 0;
    padding-bottom: 15px;
    font-size: 2.5rem;
    /* 适当增大标题字号 */
    font-weight: 700;
    /* 加粗标题 */

}

/* 元信息样式优化 */
.article-meta {
    color: #6c757d;
    /* 柔和的灰色，不抢焦点 */
    margin: 0 0 30px 0;
    padding-bottom: 20px;
    border-bottom: 1px solid #e9ecef;
    /* 分隔线 */
    font-size: 1rem;
    display: flex;
    justify-content: center;
    gap: 20px;
}

.article-meta span {
    display: flex;
    align-items: center;
    gap: 5px;
}

/* 文章内容样式优化 */
.article-content {
    width: 100%;
}

/* 右侧固定目录样式 */
.fixed-catalog {
    position: fixed;
    top: 100px;
    right: calc((100vw - 1400px) / 2 + 20px);
    width: 250px;
    max-height: calc(100vh - 140px);
    overflow-y: auto;
    background-color: rgba(251, 250, 250, 0.9);
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    margin-left: 20px;
    box-sizing: border-box;
}


</style>