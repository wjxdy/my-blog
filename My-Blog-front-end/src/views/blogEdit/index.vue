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
            <MdEditor 
                ref="editorRef"
                v-model="article.articleContext" 
                style="min-height: 700px; width: 100%;" 
                @onUploadImg="onUploadImg"
                :toolbars="toolbars"
            >
                <template #defToolbars>
                    <el-tooltip content="插入自定义尺寸图片">
                        <div class="md-editor-toolbar-item" @click="openCustomImageDialog">
                            <el-icon size="18"><Picture /></el-icon>
                        </div>
                    </el-tooltip>
                </template>
            </MdEditor>
            
            <!-- 自定义图片弹窗 -->
            <el-dialog
                v-model="customImageDialog"
                title="插入自定义尺寸图片"
                width="500px"
                align-center
            >
                <div style="padding: 20px 0;">
                    <!-- 上传区域 -->
                    <el-upload
                        v-if="!customImagePreview"
                        drag
                        action="#"
                        :auto-upload="false"
                        :on-change="(file: any) => handleLocalImageSelect(file.raw)"
                        accept="image/*"
                        style="width: 100%;"
                    >
                        <el-icon size="50" style="color: #409EFF;"><Upload /></el-icon>
                        <div style="margin-top: 10px; color: #606266;">
                            拖拽图片到此处或 <em>点击上传</em>
                        </div>
                        <div style="margin-top: 5px; color: #909399; font-size: 12px;">
                            支持 jpg/png/gif，最大 5MB
                        </div>
                    </el-upload>
                    
                    <!-- 预览和设置区域 -->
                    <div v-if="customImagePreview" style="text-align: center;">
                        <div style="margin-bottom: 20px; border: 1px solid #dcdfe6; border-radius: 8px; padding: 10px; background: #f5f7fa;">
                            <img :src="customImagePreview" style="max-width: 100%; max-height: 200px; border-radius: 4px;" />
                        </div>
                        
                        <div style="display: flex; align-items: center; gap: 15px; margin-bottom: 15px;">
                            <span style="white-space: nowrap;">显示宽度：</span>
                            <el-slider v-model="customImageWidth" :min="10" :max="100" style="flex: 1;" />
                            <span style="width: 50px; text-align: right;">{{ customImageWidth }}%</span>
                        </div>
                        
                        <div style="color: #909399; font-size: 12px; margin-bottom: 10px;">
                            提示：100% 为原始宽度，50% 为一半宽度
                        </div>
                    </div>
                    
                    <!-- 或输入URL -->
                    <el-divider v-if="!customImagePreview">或</el-divider>
                    
                    <el-input
                        v-if="!customImagePreview"
                        v-model="customImageUrl"
                        placeholder="输入图片URL"
                        clearable
                    >
                        <template #append>
                            <el-button @click="customImagePreview = customImageUrl">预览</el-button>
                        </template>
                    </el-input>
                </div>
                
                <template #footer>
                    <el-button @click="customImageDialog = false">取消</el-button>
                    <el-button 
                        type="primary" 
                        @click="uploadAndInsertCustomImage"
                        :loading="customImageLoading"
                        :disabled="!customImagePreview && !customImageUrl"
                    >
                        插入图片
                    </el-button>
                </template>
            </el-dialog>
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
                            <el-form-item label="简介">
                                <el-input v-model="article.articleIntro" :autosize="{ minRows: 2, maxRows: 4 }"
                                    type="textarea" placeholder="作品简介" style="width: 100%" />
                            </el-form-item>
                        </el-col>

                        <el-col :span="24">
                            <!-- 实现分类的下拉菜单 -->
                            <el-form-item label="标签">
                                <ElSelect v-model="article.articleTags" 
                                    multiple 
                                    placeholder="请选择文章标签"
                                    style="width: 100%;"
                                    >
                                    <!-- 下拉选项列表 -->
                                    <ElOption v-for="tag in tagList" :key="tag.articleTagId" :label="tag.articleTagName"
                                        :value="tag.articleTagId" />
                                    </ElSelect>
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
import { Picture, Upload } from '@element-plus/icons-vue';
import { watch } from 'vue'
import { MdEditor } from 'md-editor-v3';
import { addArticleApi, updateArticleApi, getArticleApi } from "@/api/article";
import 'md-editor-v3/lib/style.css';
import { ElMessage, ElMessageBox } from 'element-plus';
import { useRouter, useRoute } from 'vue-router';
import { getTagListApi } from '@/api/tag';
import { uploadImageApi } from '@/api/upload';
const router = useRouter()
const route = useRoute()
const editorRef = ref<any>(null)

// 编辑器工具栏配置
const toolbars = [
    'bold', 'underline', 'italic', 'strikeThrough', '-',
    'title', 'sub', 'sup', 'quote', 'unorderedList', 'orderedList', 'task', '-',
    'codeRow', 'code', 'link', 'image', 'table', 'mermaid', 'katex', '-',
    0, // 自定义工具栏插槽位置
    'revoke', 'next', 'save', '=',
    'prettier', 'pageFullscreen', 'fullscreen', 'preview', 'htmlPreview'
]

// 自定义图片插入状态
const customImageDialog = ref(false)
const customImageUrl = ref('')
const customImageWidth = ref(300)
const customImagePreview = ref('')
const customImageFile = ref<File | null>(null)
const customImageLoading = ref(false)

// 监听URL变化显示预览
watch(() => customImageUrl.value, (val) => {
    if (val && (val.startsWith('http://') || val.startsWith('https://'))) {
        customImagePreview.value = val
    }
})

// 打开自定义图片弹窗
const openCustomImageDialog = () => {
    customImageDialog.value = true
    customImageUrl.value = ''
    customImageWidth.value = 300
    customImagePreview.value = ''
    customImageFile.value = null
}

// 处理本地上传选择
const handleLocalImageSelect = (file: File) => {
    if (!file.type.startsWith('image/')) {
        ElMessage.error('请选择图片文件')
        return false
    }
    if (file.size > 5 * 1024 * 1024) {
        ElMessage.error('图片不能超过5MB')
        return false
    }
    
    customImageFile.value = file
    // 本地预览
    const reader = new FileReader()
    reader.onload = (e) => {
        customImagePreview.value = e.target?.result as string
    }
    reader.readAsDataURL(file)
    return false // 阻止自动上传
}

// 上传并插入图片
const uploadAndInsertCustomImage = async () => {
    let finalUrl = customImageUrl.value.trim()
    
    // 如果有本地文件，先上传
    if (customImageFile.value && !finalUrl) {
        customImageLoading.value = true
        try {
            const res = await uploadImageApi(customImageFile.value)
            if (res.code === 200 || res.code === '200') {
                finalUrl = res.data
            } else {
                ElMessage.error(res.message || '上传失败')
                customImageLoading.value = false
                return
            }
        } catch (error) {
            ElMessage.error('上传失败')
            customImageLoading.value = false
            return
        }
        customImageLoading.value = false
    }
    
    if (!finalUrl) {
        ElMessage.error('请上传图片或输入URL')
        return
    }
    
    // 生成带尺寸的图片HTML
    const width = customImageWidth.value || 100
    const imgMarkdown = `<div align="center"><img src="${finalUrl}" width="${width}%" style="max-width: 100%; border-radius: 8px;" /></div>`
    
    article.value.articleContext += '\n' + imgMarkdown + '\n'
    ElMessage.success('图片已插入')
    customImageDialog.value = false
}

const dialogVisible = ref(false)

const tagList = ref();

const loadTagList = () => {
    // 调用tagList接口  
    getTagListApi().then(res => {
        tagList.value = res.data
    })
};


const article = ref({
    articleId: "",
    username: "",
    articleTitle: "",
    articleIntro: "",
    articleAddTime: "",
    articleContext: "",
    articleTags:[]
})
const articleId = route.params.articleId
const articleUpdateData = ref();


// 根据是否有articleId判断是新增还是编辑
const handleSubmit = async () => {
    try {
        let result;
        const articleUpdateDto = ref({
            article: { ...articleUpdateData.value },
            articleTags: [article.value.articleTags.join(',')]
        });
        console.log(articleUpdateDto);
        
        if (articleId) {
            // 编辑现有文章
            result = await updateArticleApi(articleUpdateDto.value)
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
const loadArticleData = async () => {
    if (articleId) {
        try {
            const result = await getArticleApi(articleId as string)
            if (result.code == 200) {
                article.value = result.data
                articleUpdateData.value = result.data
            } else {
                ElMessage.error("加载文章失败")
            }
        } catch (error) {
            ElMessage.error("加载文章失败")
            console.error(error)
        }
    }
}


// 图片上传回调
const onUploadImg = async (files: File[], callback: (urls: string[]) => void) => {
    const urls: string[] = []
    
    for (const file of files) {
        try {
            // 验证文件类型
            if (!file.type.startsWith('image/')) {
                ElMessage.error(`${file.name} 不是图片文件`)
                continue
            }
            
            // 验证文件大小 (5MB)
            if (file.size > 5 * 1024 * 1024) {
                ElMessage.error(`${file.name} 超过5MB限制`)
                continue
            }
            
            const res = await uploadImageApi(file)
            console.log('上传响应:', res)  // 调试用
            if ((res.code === 200 || res.code === '200') && res.data) {
                urls.push(res.data)
            } else {
                ElMessage.error(res.message || `${file.name} 上传失败`)
            }
        } catch (error) {
            console.error('图片上传失败:', error)
            ElMessage.error(`${file.name} 上传失败`)
        }
    }
    
    // 只有上传成功才回传 URL 给编辑器，失败则不返回（编辑器不会显示 base64）
    if (urls.length > 0) {
        callback(urls)
        ElMessage.success(`成功上传 ${urls.length} 张图片`)
    } else {
        // 全部上传失败，传入空数组，编辑器不会插入任何内容
        callback([])
    }
}

onMounted(() => {
    loadArticleData()
    loadTagList()
})

</script>
