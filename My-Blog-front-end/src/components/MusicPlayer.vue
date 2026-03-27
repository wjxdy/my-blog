<template>
    <!-- 有音乐时显示播放器 -->
    <div class="music-player" v-if="musicList.length > 0">
        <div class="music-info">
            <el-icon class="music-icon" :class="{ rotating: isPlaying }"><Headset /></el-icon>
            <span class="music-name" :title="currentMusic?.musicName">
                {{ currentMusic?.musicName }}
            </span>
            <span v-if="currentMusic?.musicArtist" class="music-artist">
                - {{ currentMusic.musicArtist }}
            </span>
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
                size="small" 
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
</template>

<script setup lang="ts">
import { ref, onMounted, computed, watch } from 'vue'
import { Headset, VideoPlay, VideoPause, ArrowLeft, ArrowRight } from '@element-plus/icons-vue'
import { getMusicListApi } from '@/api/music'
import { ElMessage } from 'element-plus'

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
        const res = await getMusicListApi()
        if (res.code === 200 && res.data) {
            musicList.value = res.data
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
    // 自动播放
    setTimeout(() => {
        togglePlay()
    }, 100)
}

// 下一首
const nextMusic = () => {
    if (musicList.value.length <= 1) return
    currentIndex.value = (currentIndex.value + 1) % musicList.value.length
    isPlaying.value = false
    // 自动播放
    setTimeout(() => {
        togglePlay()
    }, 100)
}

// 音乐播放结束
const onMusicEnded = () => {
    // 自动播放下一首
    nextMusic()
}

// 音乐加载错误
const onMusicError = () => {
    console.error('音乐加载错误:', currentMusic.value?.musicUrl)
    ElMessage.error('音乐加载失败')
    isPlaying.value = false
}

// 监听当前音乐变化，自动播放
watch(currentIndex, () => {
    isPlaying.value = false
})

onMounted(() => {
    loadMusicList()
})
</script>

<style scoped>
.music-player {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 12px 20px;
    background: #fff;
    border-top: 1px solid #e9ecef;
    box-shadow: 0 -2px 8px rgba(0, 0, 0, 0.05);
}

.music-info {
    display: flex;
    align-items: center;
    gap: 8px;
    flex: 1;
    min-width: 0;
}

.music-icon {
    font-size: 18px;
    color: #409EFF;
    animation: rotate 3s linear infinite paused;
}

.music-icon.rotating {
    animation-play-state: running;
}

@keyframes rotate {
    from { transform: rotate(0deg); }
    to { transform: rotate(360deg); }
}

.music-name {
    font-size: 14px;
    color: #333;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    max-width: 200px;
}

.music-artist {
    font-size: 12px;
    color: #999;
}

.music-controls {
    display: flex;
    align-items: center;
    gap: 8px;
}

.play-btn {
    background: #409EFF;
    color: #fff;
    border: none;
}

.play-btn:hover {
    background: #66b1ff;
}

/* 移动端适配 */
@media screen and (max-width: 768px) {
    .music-player {
        padding: 10px 15px;
    }
    
    .music-name {
        max-width: 120px;
    }
}
</style>
