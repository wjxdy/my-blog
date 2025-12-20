<style lang="css" scoped>

.continter {
    
    
}

</style>

<template>
    <div class="continter">
        
        <MdPreview :editorId="id" :modelValue="article.articleContext || ''" />
        
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