<style scoped>
/* 整体容器样式 */
.edit-container {
    max-width: 1200px;
    margin: 0 auto;
    padding: 0px 20px;
    margin-bottom: 50px;
}

/* 导航栏样式优化 */
.navbar {
    display: flex;
    justify-content: flex-end;
    /* 靠右对齐 */
    margin-bottom: 30px;
    padding: 20px;
    background-color: #ffffff;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

/* 编辑器容器卡片式设计 */
.editor-wrapper {
    background-color: #ffffff;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    padding: 30px;
}


</style>


<template>
    <div class="edit-container">
        <div class="editor-wrapper">
            <MdEditor v-model="article.articleContext" style="min-height: 700px; width: 100%;" />
        </div>


        <div class="navbar">
            <el-button type="primary" color="#42b983" @click="dialogVisible = true" size="large">发布博客</el-button>
        </div>
        <div>
            <el-dialog v-model="dialogVisible" align-center title="作品信息" width="500">
                <div>
                    <el-row :gutter="20">
                        <el-col :span="24">
                            <!-- 用 el-form-item 的 label 替代 el-input 的 label -->
                            <el-form-item label="标题">
                                <el-input v-model="article.articleTitle" placeholder="请输入标题"></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="24">
                            <el-form-item label="作品简介">
                                <el-input v-model="article.articleIntro" :autosize="{ minRows: 2, maxRows: 4 }"
                                    type="textarea" placeholder="作品简介" style="width: 100%" />
                            </el-form-item>
                        </el-col>

                        <el-col :span="24">
                            <!-- 实现分类的下拉菜单 -->
                            <el-form-item label="分类">
                                <el-select v-model="article.articleTag" placeholder="请选择分类">
                                    <el-option
                                        v-for="item in tagList"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.name"
                                    />
                                </el-select>
                            </el-form-item>
                        </el-col>


                    </el-row>
                </div>
                <template #footer>
                    <div class="dialog-footer">
                        <el-button @click="dialogVisible = false">取消</el-button>
                        <el-button type="primary" @click="handleSubmit()">
                            确认
                        </el-button>
                    </div>
                </template>
            </el-dialog>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { MdEditor } from 'md-editor-v3';
import { addArticleApi, updateArticleApi, getArticleApi } from "@/api/article";
import 'md-editor-v3/lib/style.css';
import { ElMessage } from 'element-plus';
import { useRouter, useRoute } from 'vue-router';

const router = useRouter()
const route = useRoute()


const dialogVisible = ref(false)

const tagList = ref();

const loadTagList = () => {
    // 调用tagList接口  
};


const article = ref({
    articleId: "",
    username: "",
    articleTitle: "",
    articleTag: "",
    articleIntro: "",
    articleAddTime: "",
    articleContext: "",
    articleGoodNumber: null,
    articleLookNumber: null,
    articleCollectionNumber: null

})
const articleId = route.params.articleId



// 根据是否有articleId判断是新增还是编辑
const handleSubmit = async () => {
    try {
        let result;
        if (articleId) {
            // 编辑现有文章
            result = await updateArticleApi(article.value)
        } else {
            // 新增文章
            result = await addArticleApi(article.value)
        }

        if (result.code == 200) {
            dialogVisible.value = false
            ElMessage.success(articleId ? "更新成功" : "添加成功")
            router.push('/homePage')
        } else {
            ElMessage.error(articleId ? "更新失败" : "添加失败")
        }
    } catch (error) {
        ElMessage.error("操作失败，请稍后重试")
        console.error(error)
    }
}


onMounted(async () => {
    // 如果有articleId，加载文章数据（编辑场景）
    if (articleId) {
        try {
            const result = await getArticleApi(articleId as string)
            console.log("文章回显结果------", result)
            if (result.code == 200) {
                article.value = result.data
            } else {
                ElMessage.error("加载文章失败")
            }
        } catch (error) {
            ElMessage.error("加载文章失败")
            console.error(error)
        }
    }
})

</script>
