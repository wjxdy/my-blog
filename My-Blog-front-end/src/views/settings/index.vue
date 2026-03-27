<style scoped lang="css">
.settings-container {
    max-width: 800px;
    margin: 30px auto;
    padding: 20px;
}

.settings-card {
    background: #fff;
    border-radius: 12px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
    padding: 30px;
}

.settings-title {
    font-size: 24px;
    font-weight: 600;
    color: #2c3e50;
    margin-bottom: 30px;
    padding-bottom: 15px;
    border-bottom: 1px solid #e9ecef;
}

.avatar-section {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 30px;
}

.avatar-preview {
    width: 120px;
    height: 120px;
    border-radius: 50%;
    object-fit: cover;
    border: 2px solid #e9ecef;
    margin-bottom: 15px;
    transition: border-color 0.3s;
}

.avatar-preview:hover {
    border-color: #d0d0d0;
}

.avatar-default {
    width: 120px;
    height: 120px;
    border-radius: 50%;
    background: #fff;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #ccc;
    font-size: 48px;
    font-weight: 500;
    margin-bottom: 15px;
    border: 2px solid #e9ecef;
    transition: all 0.3s;
}

.avatar-default:hover {
    border-color: #d0d0d0;
    color: #bbb;
}

.avatar-upload-btn {
    margin-top: 10px;
}

.form-section {
    margin-top: 20px;
}

.form-item {
    margin-bottom: 25px;
}

.form-label {
    display: block;
    font-size: 14px;
    font-weight: 500;
    color: #495057;
    margin-bottom: 8px;
}

.form-input {
    width: 100%;
}

.form-hint {
    font-size: 12px;
    color: #6c757d;
    margin-top: 5px;
}

.action-section {
    display: flex;
    justify-content: flex-end;
    gap: 15px;
    margin-top: 30px;
    padding-top: 20px;
    border-top: 1px solid #e9ecef;
}

.loading-mask {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(255, 255, 255, 0.8);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1000;
}

.admin-section {
    margin-top: 30px;
    padding: 20px;
    background: #f8f9fa;
    border-radius: 8px;
}

.section-title {
    font-size: 16px;
    font-weight: 600;
    color: #333;
    margin-bottom: 15px;
    display: flex;
    align-items: center;
    gap: 8px;
}

.tag-management {
    display: flex;
    flex-direction: column;
    gap: 15px;
}

.add-tag-row {
    display: flex;
    gap: 10px;
}

.tag-input {
    flex: 1;
}

.tag-list {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
}

.tag-item {
    cursor: pointer;
}

/* 音乐管理样式 */
.music-management {
    display: flex;
    flex-direction: column;
    gap: 15px;
}

.add-music-row {
    display: flex;
    gap: 10px;
    flex-wrap: wrap;
    align-items: center;
}

.music-input {
    width: 180px;
}

.music-upload {
    display: inline-block;
}

.empty-music-hint {
    display: flex;
    align-items: center;
    justify-content: center;
    gap: 8px;
    padding: 30px;
    color: #909399;
    font-size: 14px;
    background: #f5f7fa;
    border-radius: 8px;
    border: 1px dashed #dcdfe6;
}

.music-list {
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.music-item {
    display: flex;
    align-items: center;
    gap: 10px;
    padding: 10px 15px;
    background: #f5f7fa;
    border-radius: 8px;
    transition: background 0.2s;
}

.music-item:hover {
    background: #e9ecef;
}

.music-index {
    width: 24px;
    height: 24px;
    display: flex;
    align-items: center;
    justify-content: center;
    background: #409EFF;
    color: #fff;
    border-radius: 50%;
    font-size: 12px;
}

.music-name {
    flex: 1;
    font-size: 14px;
    color: #333;
}

.music-artist {
    font-size: 13px;
    color: #999;
}
</style>

<template>
    <div class="settings-container">
        <div class="settings-card" v-loading="loading">
            <h2 class="settings-title">个人设置</h2>

            <!-- 头像区域 -->
            <div class="avatar-section">
                <img v-if="userForm.imgUrl" :src="userForm.imgUrl" class="avatar-preview" alt="头像" />
                <div v-else class="avatar-default">
                    {{ userForm.name ? userForm.name.charAt(0).toUpperCase() : 'U' }}
                </div>
                <el-upload
                    class="avatar-upload-btn"
                    action="#"
                    :auto-upload="false"
                    :show-file-list="false"
                    :on-change="handleAvatarChange"
                    accept="image/*"
                >
                    <el-button type="primary" :loading="uploading">
                        <el-icon><Upload /></el-icon>
                        更换头像
                    </el-button>
                </el-upload>
                <p class="form-hint">支持 JPG、PNG 格式，文件小于 2MB</p>
            </div>

            <!-- 表单区域 -->
            <div class="form-section">
                <div class="form-item">
                    <label class="form-label">显示名称</label>
                    <el-input
                        v-model="userForm.name"
                        placeholder="请输入显示名称"
                        size="large"
                        class="form-input"
                        maxlength="20"
                        show-word-limit
                    />
                </div>

                <div class="form-item">
                    <label class="form-label">用户名</label>
                    <el-input
                        v-model="userForm.username"
                        disabled
                        size="large"
                        class="form-input"
                    />
                    <p class="form-hint">用户名不可修改</p>
                </div>

                <div class="form-item">
                    <label class="form-label">邮箱</label>
                    <el-input
                        v-model="userForm.email"
                        placeholder="请输入邮箱地址"
                        size="large"
                        class="form-input"
                    />
                </div>

                <div class="form-item">
                    <label class="form-label">手机号</label>
                    <el-input
                        v-model="userForm.phone"
                        placeholder="请输入手机号"
                        size="large"
                        class="form-input"
                        maxlength="11"
                    />
                </div>

                <div class="form-item">
                    <label class="form-label">性别</label>
                    <el-radio-group v-model="userForm.sex" size="large">
                        <el-radio :label="1">男</el-radio>
                        <el-radio :label="0">女</el-radio>
                        <el-radio :label="2">保密</el-radio>
                    </el-radio-group>
                </div>

                <div class="form-item">
                    <label class="form-label">个人简介</label>
                    <el-input
                        v-model="userForm.bio"
                        type="textarea"
                        :rows="4"
                        placeholder="介绍一下自己..."
                        size="large"
                        class="form-input"
                        maxlength="200"
                        show-word-limit
                    />
                    <p class="form-hint">最多200字，将显示在首页个人信息卡片中</p>
                </div>

                <!-- 只有 xuleixulei 可以设置作品链接 -->
                <div class="form-item" v-if="isAdmin">
                    <label class="form-label">作品链接 (GitHub)</label>
                    <el-input
                        v-model="userForm.githubUrl"
                        placeholder="https://github.com/username"
                        size="large"
                        class="form-input"
                    />
                    <p class="form-hint">设置后导航栏"作品"将跳转到此链接（仅管理员可设置）</p>
                </div>

                <!-- 只有 xuleixulei 可以管理标签 -->
                <div class="admin-section" v-if="isAdmin">
                    <div class="section-title">
                        <el-icon><Collection /></el-icon>
                        标签管理
                    </div>
                    
                    <div class="tag-management">
                        <div class="add-tag-row">
                            <el-input
                                v-model="newTagName"
                                placeholder="输入新标签名称"
                                size="large"
                                class="tag-input"
                                @keyup.enter="addTag"
                            />
                            <el-button type="primary" size="large" :loading="addingTag" @click="addTag">
                                添加标签
                            </el-button>
                        </div>
                        
                        <div class="tag-list">
                            <el-tag
                                v-for="tag in tagList"
                                :key="tag.articleTagId"
                                class="tag-item"
                                closable
                                size="large"
                                @close="deleteTag(tag)"
                            >
                                {{ tag.articleTagName }}
                            </el-tag>
                        </div>
                    </div>
                </div>

                <!-- 只有 xuleixulei 可以管理音乐 -->
                <div class="admin-section" v-if="isAdmin">
                    <div class="section-title">
                        <el-icon><Headset /></el-icon>
                        背景音乐管理 ({{ musicList.length }}/5)
                    </div>
                    
                    <div class="music-management">
                        <div class="add-music-row">
                            <el-input
                                v-model="newMusicName"
                                placeholder="歌曲名称"
                                size="large"
                                class="music-input"
                            />
                            <el-input
                                v-model="newMusicArtist"
                                placeholder="歌手（可选）"
                                size="large"
                                class="music-input"
                            />
                            <el-upload
                                action="#"
                                :auto-upload="false"
                                :on-change="handleMusicSelect"
                                :show-file-list="false"
                                accept="audio/mp3,audio/mpeg"
                                class="music-upload"
                            >
                                <el-button size="large" :loading="uploadingMusic">
                                    {{ musicFile ? musicFile.name : '选择MP3' }}
                                </el-button>
                            </el-upload>
                            <el-button type="primary" size="large" :loading="addingMusic" @click="addMusic" :disabled="!newMusicName || !musicFile || musicList.length >= 5">
                                上传
                            </el-button>
                        </div>
                        <p class="form-hint" v-if="musicList.length >= 5" style="color: #f56c6c;">已达到最大数量限制（5首）</p>
                        
                        <div class="music-list" v-if="musicList.length > 0">
                            <div
                                v-for="(music, index) in musicList"
                                :key="music.musicId"
                                class="music-item"
                            >
                                <span class="music-index">{{ index + 1 }}</span>
                                <span class="music-name">{{ music.musicName }}</span>
                                <span v-if="music.musicArtist" class="music-artist">- {{ music.musicArtist }}</span>
                                <el-button
                                    type="danger"
                                    link
                                    size="small"
                                    @click="deleteMusic(music)"
                                >
                                    删除
                                </el-button>
                            </div>
                        </div>
                        <div v-else class="empty-music-hint">
                            <el-icon><Headset /></el-icon>
                            <span>暂无背景音乐，请上传 MP3 文件</span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 操作按钮 -->
            <div class="action-section">
                <el-button size="large" @click="resetForm">重置</el-button>
                <el-button type="primary" size="large" :loading="saving" @click="saveSettings">
                    保存修改
                </el-button>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Upload, Collection, Headset } from '@element-plus/icons-vue'
import { getUserInfoApi, updateUserInfoApi, type UserInfo } from '@/api/user'
import { getTagListApi, addTagApi, deleteTagApi } from '@/api/tag'
import { getMusicListApi, uploadMusicApi, deleteMusicApi } from '@/api/music'
import { uploadImageApi, uploadFileApi } from '@/api/upload'
import { uploadAvatarApi } from '@/api/upload'
import { useUserInfoStore } from '@/stores/counter'

const userInfoStore = useUserInfoStore()

const loading = ref(false)
const saving = ref(false)
const uploading = ref(false)
const addingTag = ref(false)
const newTagName = ref('')
const tagList = ref<any[]>([])

// 音乐管理
const addingMusic = ref(false)
const uploadingMusic = ref(false)
const newMusicName = ref('')
const newMusicArtist = ref('')
const musicFile = ref<File | null>(null)
const musicList = ref<any[]>([])

// 用户表单数据
const userForm = ref<Partial<UserInfo>>({
    name: '',
    username: '',
    email: '',
    phone: '',
    sex: 2,
    imgUrl: '',
    bio: '',
    githubUrl: ''
})

// 原始数据，用于重置
const originalData = ref<Partial<UserInfo>>({})

// 判断是否是管理员（xuleixulei）
const isAdmin = computed(() => {
    return userForm.value.username === 'xuleixulei'
})

// 加载用户信息
const loadUserInfo = async () => {
    loading.value = true
    try {
        const res = await getUserInfoApi()
        // 兼容字符串和数字类型的code
        if ((res.code == 200 || res.code == '200') && res.data) {
            userForm.value = { ...res.data }
            originalData.value = { ...res.data }
            // 加载作品链接到 store
            if (res.data.githubUrl) {
                userInfoStore.blogSettings.githubUrl = res.data.githubUrl
            }
            console.log('加载用户信息成功, imgUrl:', res.data.imgUrl)
        } else {
            ElMessage.error(res.message || '获取用户信息失败')
        }
    } catch (error) {
        console.error('获取用户信息失败:', error)
        ElMessage.error('获取用户信息失败')
    } finally {
        loading.value = false
    }
}

// 处理头像变更
const handleAvatarChange = async (file: any) => {
    const rawFile = file.raw
    if (!rawFile) return

    // 验证文件类型
    if (!rawFile.type.startsWith('image/')) {
        ElMessage.error('请上传图片文件')
        return
    }

    // 验证文件大小 (2MB)
    if (rawFile.size > 2 * 1024 * 1024) {
        ElMessage.error('头像文件大小不能超过2MB')
        return
    }

    uploading.value = true
    try {
        const res = await uploadAvatarApi(rawFile)
        console.log('上传头像响应:', res)
        // 注意：后端返回的code是字符串或数字，都要兼容
        if ((res.code == 200 || res.code == '200') && res.data) {
            const imgUrl = res.data
            console.log('上传头像URL:', imgUrl)
            // 立即保存头像URL到数据库
            const params = { imgUrl: imgUrl }
            console.log('发送更新请求参数:', params)
            const saveRes = await updateUserInfoApi(params)
            console.log('保存头像响应:', saveRes)
            if (saveRes.code == 200 || saveRes.code == '200') {
                userForm.value.imgUrl = imgUrl
                originalData.value.imgUrl = imgUrl
                ElMessage.success('头像更新成功')
            } else {
                ElMessage.error(saveRes.message || '头像保存失败')
            }
        } else {
            ElMessage.error(res.message || '头像上传失败')
        }
    } catch (error) {
        console.error('头像上传失败:', error)
        ElMessage.error('头像上传失败')
    } finally {
        uploading.value = false
    }
}

// 重置表单
const resetForm = () => {
    userForm.value = { ...originalData.value }
    ElMessage.info('已重置为保存的设置')
}

// 保存设置
const saveSettings = async () => {
    if (!userForm.value.name?.trim()) {
        ElMessage.warning('请输入显示名称')
        return
    }

    saving.value = true
    try {
        const params: any = {
            name: userForm.value.name,
            email: userForm.value.email,
            phone: userForm.value.phone,
            sex: userForm.value.sex,
            imgUrl: userForm.value.imgUrl,
            bio: userForm.value.bio
        }
        // 只有管理员可以保存 githubUrl
        if (isAdmin.value && userForm.value.githubUrl) {
            params.githubUrl = userForm.value.githubUrl
        }
        const res = await updateUserInfoApi(params)
        if (res.code == 200 || res.code == '200') {
            ElMessage.success('保存成功')
            if (res.data) {
                userForm.value = { ...res.data }
                originalData.value = { ...res.data }
                // 更新全局store中的用户信息
                userInfoStore.loginData.name = res.data.name
                // 更新作品链接
                if (res.data.githubUrl) {
                    userInfoStore.blogSettings.githubUrl = res.data.githubUrl
                }
            }
        } else {
            ElMessage.error(res.message || '保存失败')
        }
    } catch (error) {
        console.error('保存设置失败:', error)
        ElMessage.error('保存失败')
    } finally {
        saving.value = false
    }
}

// 加载标签列表
const loadTagList = async () => {
    try {
        const res = await getTagListApi()
        if (res.code == 200 || res.code == '200') {
            tagList.value = res.data || []
        }
    } catch (error) {
        console.error('加载标签列表失败:', error)
    }
}

// 添加标签
const addTag = async () => {
    if (!newTagName.value.trim()) {
        ElMessage.error('标签名称不能为空')
        return
    }
    addingTag.value = true
    try {
        const res = await addTagApi(newTagName.value.trim())
        if (res.code == 200 || res.code == '200') {
            ElMessage.success('添加成功')
            tagList.value.push(res.data)
            newTagName.value = ''
        } else {
            ElMessage.error(res.message || '添加失败')
        }
    } catch (error) {
        ElMessage.error('添加失败')
    } finally {
        addingTag.value = false
    }
}

// 删除标签
const deleteTag = async (tag: any) => {
    try {
        await ElMessageBox.confirm(
            `确定要删除标签 "${tag.articleTagName}" 吗？`,
            '确认删除',
            {
                confirmButtonText: '删除',
                cancelButtonText: '取消',
                type: 'warning'
            }
        )
        const res = await deleteTagApi(tag.articleTagId)
        if (res.code == 200 || res.code == '200') {
            ElMessage.success('删除成功')
            tagList.value = tagList.value.filter(t => t.articleTagId !== tag.articleTagId)
        } else {
            ElMessage.error(res.message || '删除失败')
        }
    } catch (error) {
        if (error !== 'cancel') {
            ElMessage.error('删除失败')
        }
    }
}

// 加载音乐列表
const loadMusicList = async () => {
    try {
        const res = await getMusicListApi()
        if (res.code == 200 || res.code == '200') {
            musicList.value = res.data || []
        }
    } catch (error) {
        console.error('加载音乐列表失败:', error)
    }
}

// 选择音乐文件
const handleMusicSelect = (file: any) => {
    const rawFile = file.raw
    if (!rawFile) return

    // 验证文件类型
    if (!rawFile.type.startsWith('audio/') && !rawFile.name.endsWith('.mp3')) {
        ElMessage.error('请上传MP3格式的音乐文件')
        return
    }

    // 验证文件大小 (50MB)
    if (rawFile.size > 50 * 1024 * 1024) {
        ElMessage.error('音乐文件大小不能超过50MB')
        return
    }

    musicFile.value = rawFile
    // 自动提取文件名作为歌曲名
    if (!newMusicName.value) {
        const fileName = rawFile.name.replace('.mp3', '').replace('.MP3', '')
        newMusicName.value = fileName
    }
}

// 添加音乐
const addMusic = async () => {
    if (!newMusicName.value.trim() || !musicFile.value) {
        ElMessage.error('请填写歌曲名称并选择文件')
        return
    }
    
    if (musicList.value.length >= 5) {
        ElMessage.error('最多只能上传5首歌曲')
        return
    }

    addingMusic.value = true
    uploadingMusic.value = true
    
    try {
        // 1. 上传音乐文件到OSS
        const uploadRes = await uploadFileApi(musicFile.value)
        
        if (uploadRes.code == 200 || uploadRes.code == '200') {
            const musicUrl = uploadRes.data
            
            // 2. 保存音乐信息到数据库
            const saveRes = await uploadMusicApi({
                musicName: newMusicName.value.trim(),
                musicUrl: musicUrl,
                musicArtist: newMusicArtist.value.trim() || undefined
            })
            
            if (saveRes.code == 200 || saveRes.code == '200') {
                ElMessage.success('上传成功')
                musicList.value.push(saveRes.data)
                newMusicName.value = ''
                newMusicArtist.value = ''
                musicFile.value = null
            } else {
                ElMessage.error(saveRes.message || '保存失败')
            }
        } else {
            ElMessage.error(uploadRes.message || '上传失败')
        }
    } catch (error) {
        console.error('上传音乐失败:', error)
        ElMessage.error('上传失败')
    } finally {
        addingMusic.value = false
        uploadingMusic.value = false
    }
}

// 删除音乐
const deleteMusic = async (music: any) => {
    console.log('点击删除音乐:', music)
    try {
        const result = await ElMessageBox.confirm(
            `确定要删除歌曲 "${music.musicName}" 吗？`,
            '确认删除',
            {
                confirmButtonText: '删除',
                cancelButtonText: '取消',
                type: 'warning'
            }
        )
        console.log('确认删除结果:', result)
        
        // 检查是否有登录信息
        const userInfo = localStorage.getItem('userInfo')
        console.log('删除音乐 - userInfo:', userInfo ? '存在' : '不存在')
        
        const res = await deleteMusicApi(music.musicId)
        console.log('删除音乐响应:', res)
        if (res.code == 200 || res.code == '200') {
            ElMessage.success('删除成功')
            musicList.value = musicList.value.filter(m => m.musicId !== music.musicId)
        } else {
            ElMessage.error(res.message || '删除失败')
        }
    } catch (error: any) {
        console.log('删除音乐错误:', error)
        if (error === 'cancel' || error?.message === 'cancel') {
            console.log('用户取消删除')
        } else {
            ElMessage.error('删除失败')
        }
    }
}

onMounted(() => {
    loadUserInfo()
    loadTagList()
    loadMusicList()
})
</script>
