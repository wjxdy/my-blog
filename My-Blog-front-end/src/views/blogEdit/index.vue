
<style lang="css" scoped>
.navbar {
    display: flex;
    align-items: center;
    padding: 10px 20px;
    background-color: #fff;
    box-shadow: 0 -2px 4px rgba(0, 0, 0, 0.1);
    margin-top: 20px;
}


</style>


<template>
    <MdEditor v-model="article.articleContext" style="min-height: 600px;" />


    <nav class="navbar">

        <div>

            <el-button style="margin-left: 800px" color="#626aef" @click="dialogVisible=true" plain
                size="large">发布博客</el-button>

        </div>
        <div>
            <el-dialog v-model="dialogVisible" align-center title="作品信息" width="500" >
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
                                <el-input v-model="article.articleIntro" style="width: 240px"
                                    :autosize="{ minRows: 2, maxRows: 4 }" type="textarea" placeholder="作品简介" />
                            </el-form-item>
                        </el-col>

                    </el-row>
                </div>
                <template #footer>
                    <div class="dialog-footer">
                        <el-button @click="dialogVisible = false">取消</el-button>
                        <el-button type="primary" @click="handleSubmit ()">
                            确认
                        </el-button>
                    </div>
                </template>
            </el-dialog>
        </div>

    </nav>
</template>

<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { MdEditor } from 'md-editor-v3';
import { addArticleApi,updateArticleApi,getArticleApi } from "@/api/article";
import 'md-editor-v3/lib/style.css';
import { ElMessage } from 'element-plus';
import { useRouter,useRoute } from 'vue-router';

const router = useRouter()
const route = useRoute()


const dialogVisible = ref(false)

const article = ref({
    articleId: "",
    readerUsername: "",
    articleTitle: "",
    articleIntro: "",
    articleAddTime: "",
    articleContext: "",
    articleGoodNumber: 0,
    articleLookNumber: 0,
    articleCollectionNumber: 0

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
            console.log("文章回显结果------",result)
            if (result.code == 200) {
                article.value = result.data
            } else {
                ElMessage.error("加载文章失败")
            }
        } catch (error) {
            ElMessage.error("加载文章时发生错误")
            console.error(error)
        }
    }
})

</script>
