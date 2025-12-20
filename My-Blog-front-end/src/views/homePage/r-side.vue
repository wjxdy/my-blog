<style lang="css" scoped>
.updates-panel {
    
}

.updates-title {
    font-size: 1.2rem;
    font-weight: 600;
    
    margin-bottom: 20px;
    padding-bottom: 10px;
    
    display: flex;
    align-items: center;
    gap: 10px;
}

.update-section {
    margin-bottom: 30px;
}

.section-header {
    font-size: 1rem;
    font-weight: 500;
    color: #555;
    margin-bottom: 15px;
    display: flex;
    align-items: center;
    gap: 8px;
}


</style>

<template>
    <div class="updates-panel">
        <div class="updates-title">
            <MdPreview :editorId="id" :modelValue="article.articleContext || ''" />
        </div>
    </div>
</template>


<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import { getLogApi } from '@/api/article';
import { MdPreview} from 'md-editor-v3';
import 'md-editor-v3/lib/preview.css';

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
const id = 'preview-only';
const loadLog = async () => { 

    const res = await getLogApi();
    article.value = res.data;
}

onMounted(() => {
    loadLog()
});
</script>