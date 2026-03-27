<style scoped>
/* 整体布局样式 */
.parent {
    display: flex;
    min-height: auto;
    width: 100%;
    max-width: 1500px;
    margin: 0 auto;
    padding: 0 20px;
    box-sizing: border-box;
    gap: 30px;
    align-items: flex-start;
    /* 使子项根据自身内容高度显示，不拉伸 */
}
/* 左侧整体容器 */
.left-container {
    display: flex;
    flex-direction: column;
    width: 260px;
    flex-shrink: 0;
    position: sticky;
    top: 20px;
    align-self: flex-start;
}

/* 中间内容区域样式 */
.middle {
    flex-grow: 1;
    padding: 10px 20px;
    background-color: #fff;
}

/* 右侧边栏样式 */
.r-side {
    width: 260px;
    background-color: #fff;
    flex-shrink: 0;
    position: sticky;
    top: 20px;
    align-self: flex-start;
    display: flex;
    flex-direction: column;
    gap: 20px;
}

/* ==================== 手机端响应式设计 ==================== */
@media screen and (max-width: 1024px) {
    .parent {
        flex-direction: column;
        padding: 0 10px;
        gap: 15px;
    }
    
    /* 左侧边栏隐藏 */
    .left-container {
        display: none;
    }
    
    /* 右侧边栏放到底部 */
    .r-side {
        width: 100%;
        order: 3;
    }
    
    /* 中间内容全宽 */
    .middle {
        width: 100%;
        padding: 10px;
    }
}

@media screen and (max-width: 768px) {
    .parent {
        padding: 0 8px;
        gap: 10px;
        margin: 5px auto;
    }
    
    /* 隐藏左右边栏，只保留中间内容 */
    .left-container,
    .r-side {
        display: none;
    }
    
    /* 中间内容全宽显示 */
    .middle {
        width: 100%;
        padding: 8px;
        border-radius: 8px;
    }
    
    /* 博客列表样式调整 */
    .blog-item {
        padding: 12px;
        margin-bottom: 10px;
        border-radius: 6px;
    }
    
    .blog-title {
        font-size: 1.1rem;
        margin-bottom: 10px;
    }
    
    .blog-meta {
        font-size: 0.8rem;
        gap: 10px;
        margin-bottom: 10px;
    }
    
    /* 预览内容高度调整 */
    .blog-content-preview :deep(.md-preview-wrapper) {
        max-height: 250px;
    }
    
    /* 分页器调整 */
    .demo-pagination-block {
        padding: 10px 0;
    }
    
    :deep(.el-pagination) {
        justify-content: center;
        flex-wrap: wrap;
    }
}

.blog-item {
    padding: 20px;
    background: #fff;
    max-width: 100%;
    border-radius: 8px;
    border: 1px solid #e9ecef;
    margin: 0 auto 15px;
    /* 关键：左右auto实现居中，保留底部margin */
    transition: transform 0.2s;
}



/* 博客标题样式 */
.blog-title {
    color: #2c3e50;
    margin: 0 0 8px 0;
    cursor: pointer;
    font-size: 1.5rem;
    font-weight: 700;
    text-decoration: none;
    display: inline-block;
    transition: all 0.3s ease;
    line-height: 1.4;
}



/* 博客元信息样式 */
.blog-meta {
    color: #6c757d;
    font-size: 0.95rem;
    margin-bottom: 8px;
    display: flex;
    gap: 25px;
    align-items: center;
}

/* 博客内容链接样式 - 可点击跳转 */
.blog-content-link {
    text-decoration: none;
    color: inherit;
    display: block;
    cursor: pointer;
}

.blog-content-link:hover {
    color: inherit;
}

/* 博客内容预览样式 */
.blog-content-preview {
    position: relative;
}

.blog-content-preview :deep(.md-preview) {
    background: transparent !important;
}

/* 缩小预览内容的字体大小 */
.blog-content-preview :deep(.md-preview) {
    font-size: 14px;
    line-height: 1.6;
}

/* 限制预览区域的最大高度 */
.blog-content-preview :deep(.md-preview-wrapper) {
    max-height: 300px;
    overflow: hidden;
}

/* 文章标签样式 - 类似美团 */
.article-tags {
    display: flex;
    flex-wrap: wrap;
    gap: 16px;
    margin-top: 12px;
}

.article-tag {
    font-size: 13px;
    color: #666;
    text-decoration: none;
    border-bottom: 1px solid #ccc;
    padding-bottom: 2px;
    line-height: 1.4;
    transition: all 0.2s ease;
}

.article-tag:hover {
    border-bottom-color: #666;
}

/* 博客标题悬停下横线效果 - 美团风格 */
.blog-title {
    color: #2c3e50;
    margin: 0 0 8px 0;
    cursor: pointer;
    font-size: 1.5rem;
    font-weight: 700;
    text-decoration: none;
    display: inline-block;
    transition: all 0.3s ease;
    line-height: 1.4;
    border-bottom: 2px solid transparent;
    padding-bottom: 4px;
}

.blog-title:hover {
    border-bottom-color: #333;
}

/* 分割线样式 */
hr {
    border: none;
    border-top: 1px solid #e9ecef;
    margin: 15px 0;
}

/* 音乐播放器样式 */
.music-section {
    margin-top: 20px;
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    padding: 20px;
}

.section-title {
    font-size: 16px;
    font-weight: 600;
    color: #2c3e50;
    margin-bottom: 15px;
    display: flex;
    align-items: center;
    gap: 8px;
}

.music-player-card {
    display: flex;
    flex-direction: column;
    gap: 15px;
}

.music-info {
    display: flex;
    align-items: center;
    gap: 12px;
}

.music-icon {
    font-size: 24px;
    color: #666;
    animation: rotate 3s linear infinite paused;
}

.music-icon.rotating {
    animation-play-state: running;
}

@keyframes rotate {
    from { transform: rotate(0deg); }
    to { transform: rotate(360deg); }
}

.music-detail {
    flex: 1;
    min-width: 0;
}

.music-name {
    font-size: 14px;
    color: #333;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    font-weight: 500;
}

.music-artist {
    font-size: 12px;
    color: #999;
    margin-top: 2px;
}

.music-controls {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 12px;
}

.play-btn {
    background: #333;
    color: #fff;
    border: none;
}

.play-btn:hover {
    background: #555;
}
</style>
<template>
    <div class="parent">
        <div class="left-container">
            <profile-card />
            <price-chart-view />
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

                <!-- 渲染博客内容摘要 - 点击可跳转 -->
                <router-link :to="`/article/${article.articleId}`" class="blog-content-link">
                    <div class="blog-content-preview">
                        <MdPreview 
                            :editorId="'preview-' + article.articleId" 
                            :modelValue="getPreviewContent(article.articleContext)" 
                        />
                    </div>
                </router-link>
                
                <!-- 文章标签 -->
                <div class="article-tags" v-if="article.tags && article.tags.length > 0">
                    <router-link 
                        v-for="(tag, tagIndex) in article.tags" 
                        :key="tag?.articleTagId || tagIndex"
                        :to="`/tag/${tag?.articleTagId}`"
                        class="article-tag"
                    >
                        {{ tag?.articleTagName || '未知标签' }}
                    </router-link>
                </div>
            </div>
            <div class="demo-pagination-block">
                <el-pagination v-model:current-page="userInfoStore.pageInfo.currentPage" v-model:page-size="userInfoStore.pageInfo.pageSize"
                    :page-sizes="[3, 5, 10, 20, 50]" size="large" layout="total, sizes, prev, pager, next, jumper"
                    :total="userInfoStore.pageInfo.pageTotal" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
            </div>
        </div>
        <div class="r-side">
            <tags-cloud />
            <!-- 音乐播放器 -->
            <div class="music-section" v-if="musicList.length > 0">
                <div class="music-player-card">
                    <div class="music-info">
                        <el-icon class="music-icon" :class="{ rotating: isPlaying }"><Headset /></el-icon>
                        <div class="music-detail">
                            <div class="music-name" :title="currentMusic?.musicName">
                                {{ currentMusic?.musicName }}
                            </div>
                            <div v-if="currentMusic?.musicArtist" class="music-artist">
                                {{ currentMusic.musicArtist }}
                            </div>
                        </div>
                    </div>
                    
                    <div class="music-controls">
                        <el-button 
                            circle 
                            size="small" 
                            @click="prevMusic"
                            :disabled="musicList.length <= 1"
                        >
                            <el-icon><ArrowLeft /></el-icon>
                        </el-button>
                        
                        <el-button 
                            circle 
                            size="default" 
                            @click="togglePlay"
                            class="play-btn"
                        >
                            <el-icon v-if="isPlaying"><VideoPause /></el-icon>
                            <el-icon v-else><VideoPlay /></el-icon>
                        </el-button>
                        
                        <el-button 
                            circle 
                            size="small" 
                            @click="nextMusic"
                            :disabled="musicList.length <= 1"
                        >
                            <el-icon><ArrowRight /></el-icon>
                        </el-button>
                    </div>
                    
                    <audio 
                        ref="audioRef" 
                        :src="currentMusic?.musicUrl" 
                        @ended="onMusicEnded"
                        @error="onMusicError"
                        style="display: none;"
                    ></audio>
                </div>
            </div>
        </div>
    </div>
</template>
<script setup lang="ts">
import { getArticlePageApi } from "@/api/article";
import { ref, onMounted, computed } from "vue";
import { ElMessage } from 'element-plus';
import ProfileCard from './profile-card.vue'
import TagsCloud from './tags-cloud.vue'
import priceChartView from './priceChart.vue'
import { formatDate } from "@/utils/dateUtils";
import { Headset, VideoPlay, VideoPause, ArrowLeft, ArrowRight } from '@element-plus/icons-vue'
import { getMusicListApi } from '@/api/music'
import { useUserInfoStore } from "@/stores/counter";
import { MdPreview } from 'md-editor-v3';
import 'md-editor-v3/lib/preview.css';
const userInfoStore = useUserInfoStore();

// ========== 音乐播放器 ==========
interface Music {
    musicId: string;
    musicName: string;
    musicUrl: string;
    musicArtist: string;
    sortOrder: number;
}

const musicList = ref<Music[]>([])
const currentIndex = ref(0)
const isPlaying = ref(false)
const audioRef = ref<HTMLAudioElement>()

const currentMusic = computed(() => {
    return musicList.value[currentIndex.value] || null
})

// 加载音乐列表
const loadMusicList = async () => {
    try {
        const res: any = await getMusicListApi()
        console.log('音乐列表API响应:', res)
        // 支持数字和字符串类型的code
        const code = res.code
        if ((code === 200 || code === '200') && res.data) {
            musicList.value = res.data
            console.log('音乐列表加载成功:', musicList.value)
        } else {
            console.log('音乐列表加载失败，code:', code, 'data:', res.data)
        }
    } catch (error) {
        console.error('加载音乐列表失败:', error)
    }
}

// 播放/暂停
const togglePlay = () => {
    if (!audioRef.value) return
    
    if (isPlaying.value) {
        audioRef.value.pause()
        isPlaying.value = false
    } else {
        audioRef.value.play().then(() => {
            isPlaying.value = true
        }).catch(err => {
            console.error('播放失败:', err)
            ElMessage.error('音乐播放失败')
        })
    }
}

// 上一首
const prevMusic = () => {
    if (musicList.value.length <= 1) return
    currentIndex.value = (currentIndex.value - 1 + musicList.value.length) % musicList.value.length
    isPlaying.value = false
    setTimeout(() => togglePlay(), 100)
}

// 下一首
const nextMusic = () => {
    if (musicList.value.length <= 1) return
    currentIndex.value = (currentIndex.value + 1) % musicList.value.length
    isPlaying.value = false
    setTimeout(() => togglePlay(), 100)
}

// 音乐播放结束
const onMusicEnded = () => {
    nextMusic()
}

// 音乐加载错误
const onMusicError = () => {
    console.error('音乐加载错误:', currentMusic.value?.musicUrl)
    ElMessage.error('音乐加载失败')
    isPlaying.value = false
}
// ========== 音乐播放器结束 ==========
    
const handleSizeChange = (val: number) => {
    userInfoStore.pageInfo.pageSize = val;
    userInfoStore.pageInfo.currentPage = 1;
    getArticlePage();
};

const handleCurrentChange = (val: number) => {
    userInfoStore.pageInfo.currentPage = val;
    getArticlePage();
};

// 获取预览内容（保留图片和格式）
const getPreviewContent = (content: string | undefined) => {
    if (!content) return ''
    
    // 移除 base64 图片（太长无法渲染），保留普通图片和文字
    let processed = content.replace(/!\[([^\]]*)\]\(data:image\/[^;]+;base64,[^)]+\)/g, '')
    
    // 截取前600字符
    if (processed.length > 600) {
        return processed.slice(0, 600) + '\n\n...'
    }
    
    return processed
}

const getArticlePage = async () => {
    try {
        
        const result = await getArticlePageApi(useUserInfoStore().pageInfo);

        if (result.code == 200) {
            // 处理文章数据，过滤掉 null 的标签
            const articles = result.data.data.map((article: any) => {
                if (article.tags) {
                    article.tags = article.tags.filter((tag: any) => tag && tag.articleTagId && tag.articleTagName)
                }
                return article
            })
            userInfoStore.ArticleList = articles;
            userInfoStore.pageInfo.pageTotal = result.data.pageTotal;
            console.log('文章列表:', articles)
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
    loadMusicList();
});

</script>