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
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Upload } from '@element-plus/icons-vue'
import { getUserInfoApi, updateUserInfoApi, type UserInfo } from '@/api/user'
import { uploadAvatarApi } from '@/api/upload'
import { useUserInfoStore } from '@/stores/counter'

const userInfoStore = useUserInfoStore()

const loading = ref(false)
const saving = ref(false)
const uploading = ref(false)

// 用户表单数据
const userForm = ref<Partial<UserInfo>>({
    name: '',
    username: '',
    email: '',
    phone: '',
    sex: 2,
    imgUrl: ''
})

// 原始数据，用于重置
const originalData = ref<Partial<UserInfo>>({})

// 加载用户信息
const loadUserInfo = async () => {
    loading.value = true
    try {
        const res = await getUserInfoApi()
        // 兼容字符串和数字类型的code
        if ((res.code == 200 || res.code == '200') && res.data) {
            userForm.value = { ...res.data }
            originalData.value = { ...res.data }
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
        const res = await updateUserInfoApi({
            name: userForm.value.name,
            email: userForm.value.email,
            phone: userForm.value.phone,
            sex: userForm.value.sex,
            imgUrl: userForm.value.imgUrl
        })
        if (res.code == 200 || res.code == '200') {
            ElMessage.success('保存成功')
            if (res.data) {
                userForm.value = { ...res.data }
                originalData.value = { ...res.data }
                // 更新全局store中的用户信息
                userInfoStore.loginData.name = res.data.name
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

onMounted(() => {
    loadUserInfo()
})
</script>
