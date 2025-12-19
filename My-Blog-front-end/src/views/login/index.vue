<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { userLoginApi, sendEmailCodeApi, verifyEmailCodeApi, userRegisterApi } from '@/api/user'
import router from '@/router'
import { useUserInfoStore } from '@/stores/counter'

// 切换登录/注册模式
const isRegisterMode = ref(false)

// 登录表单数据
const loginForm = reactive({
  username: "",
  password: ""
})

// 注册表单数据
const registerForm = reactive({
  username: "",
  name: "",
  email: "",
  code: "",
  password: ""
})

// 表单引用
const ruleFormRef = ref<FormInstance>()
const registerFormRef = ref<FormInstance>()

// 验证码倒计时
const countdown = ref(0)
const isCounting = ref(false)

// 登录表单校验规则
const loginRules = reactive<FormRules>({
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
    { min: 3, max: 10, message: '账号长度在 3 到 10 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 3, message: '密码长度不能少于 3 个字符', trigger: 'blur' }
  ]
})

// 注册表单校验规则
const registerRules = reactive<FormRules>({
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
    { min: 3, max: 10, message: '账号长度在 3 到 10 个字符', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 1, max: 10, message: '用户名长度在 1 到 10 个字符', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入有效的邮箱地址', trigger: 'blur' }
  ],
  code: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
    { min: 6, max: 6, message: '验证码长度为6位', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 3, message: '密码长度不能少于 3 个字符', trigger: 'blur' }
  ]
})

// 提交登录表单
const submitLoginForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate(async (valid) => {
    if (valid) {
      // 登录校验逻辑
      const result = await userLoginApi(loginForm)
      if (result.code === '200') {
        localStorage.setItem('userInfo', JSON.stringify(result.data))

        // 保存信息到pinia中
        const userStore = useUserInfoStore()
        userStore.loginData = result.data || {
          sex: '',
          id: '',
          name: '',
          token: '',
          username: ''
        }

        ElMessage.success("登录成功，正在跳转...")
        router.push("/")
      } else {
        ElMessage.error(result.message || "账号或密码错误")
      }
    }
  })
}

// 提交注册表单
const submitRegisterForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate(async (valid) => {
    if (valid) {
      // 先验证邮箱验证码
      const verifyResult = await verifyEmailCodeApi({
        emailAddress: registerForm.email,
        code: registerForm.code
      })

      if (verifyResult.code === '200') {
        // 验证码验证通过，执行注册
        const registerResult = await userRegisterApi(registerForm)
        if (registerResult.code === '200') {
          ElMessage.success("注册成功，请登录")
          isRegisterMode.value = false
        } else {
          ElMessage.error(registerResult.message || "注册失败")
        }
      } else {
        ElMessage.error(verifyResult.message || "验证码错误")
      }
    }
  })
}

// 发送验证码
const sendCode = async () => {
  if (!registerForm.email) {
    ElMessage.warning("请先输入邮箱")
    return
  }

  // 简单邮箱格式验证
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  if (!emailRegex.test(registerForm.email)) {
    ElMessage.warning("请输入有效的邮箱地址")
    return
  }

  // 发送验证码
  const result = await sendEmailCodeApi(registerForm.email)
  if (result.code === '200') {
    ElMessage.success("验证码发送成功")
    // 开始倒计时
    startCountdown()
  } else {
    ElMessage.error(result.message || "验证码发送失败")
  }
}

// 倒计时函数
const startCountdown = () => {
  countdown.value = 60
  isCounting.value = true

  const timer = setInterval(() => {
    countdown.value--
    if (countdown.value <= 0) {
      clearInterval(timer)
      isCounting.value = false
    }
  }, 1000)
}

// 重置表单
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>

<template>
  <!-- 外层容器 -->
  <div class="login-container">
    <!-- 右侧登录表单区域（完全居中） -->
    <div class="right-section">
      <div class="login-form-container">
        <!-- 登录表单 -->
        <el-form v-if="!isRegisterMode" ref="ruleFormRef" :model="loginForm" :rules="loginRules" status-icon
          class="login-form">
          <el-form-item prop="username">
            <el-input v-model="loginForm.username" placeholder="账号" autocomplete="off" />
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="loginForm.password" type="password" placeholder="密码" autocomplete="off" />
          </el-form-item>
          <el-form-item class="form-actions">
            <el-button type="primary" @click="submitLoginForm(ruleFormRef)" style="width: 100%;">
              登录
            </el-button>
          </el-form-item>
          <div class="mode-switch-text">
            <!-- <span>还没有账号？</span> -->
            <!-- <el-button type="text" @click="isRegisterMode = true">立即注册</el-button> -->
          </div>
        </el-form>

        <!-- 注册表单 -->
        <el-form v-else ref="registerFormRef" :model="registerForm" :rules="registerRules" status-icon
          class="login-form">
          <h3 class="form-title">创建新账号</h3>
          <el-form-item prop="username">
            <el-input v-model="registerForm.username" placeholder="账号" autocomplete="off" />
          </el-form-item>
          <el-form-item prop="name">
            <el-input v-model="registerForm.name" placeholder="用户名（最多10个字符）" autocomplete="off" />
          </el-form-item>
          <el-form-item prop="email">
            <el-input v-model="registerForm.email" placeholder="邮箱" autocomplete="off" />
          </el-form-item>
          <el-form-item prop="code">
            <div class="code-input-container">
              <el-input v-model="registerForm.code" placeholder="验证码" autocomplete="off" style="width: 180px;" />
              <el-button type="primary" :disabled="isCounting" @click="sendCode" style="margin-left: 10px;">
                {{ isCounting ? `${countdown}s后重新获取` : '获取验证码' }}
              </el-button>
            </div>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="registerForm.password" type="password" placeholder="密码" autocomplete="off" />
          </el-form-item>
          <el-form-item class="form-actions">
            <el-button type="primary" @click="submitRegisterForm(registerFormRef)" style="width: 100%;">
              注册
            </el-button>
          </el-form-item>
          <div class="mode-switch-text">
            <span>已有账号？</span>
            <el-button type="text" @click="isRegisterMode = false">立即登录</el-button>
          </div>
        </el-form>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* 全局样式重置（避免默认边距影响） */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* 登录容器：全屏+背景图自适应 */
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
  /* 用100vh确保完全占满屏幕高度 */
  background-image: url('../../assets/myHeaderlogo/BackGround2.png');
  background-size: contain;
  /* 背景图覆盖全屏，保持比例（推荐） */
  background-position: center center;
  background-repeat: no-repeat;
  background-attachment: fixed;
  /* 背景图固定，不随滚动移动 */
  overflow: hidden;
  /* 隐藏溢出部分 */
}



/* 右侧表单区域：完全居中+白色背景+阴影优化 */
.right-section {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 40px;
  background-color: rgba(255, 255, 255, 0.7);
  /* 半透明白色背景，更美观 */
  border-radius: 12px;
  /* 圆角优化 */
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  /* 柔和阴影 */
  width: 100%;
  max-width: 450px;
  /* 限制最大宽度，避免大屏下过宽 */
  margin: 0 20px;
  /* 左右留白，小屏幕不贴边 */
}

/* 表单容器：垂直排列+居中对齐 */
.login-form-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 25px;
}

/* 表单标题样式 */
.form-title {
  font-size: 22px;
  font-weight: 600;
  color: #333;
  margin-bottom: 10px;
  text-align: center;
}

/* 表单样式：水平居中+宽度适配 */
.login-form {
  width: 100%;
  max-width: 350px;
  /* 表单最大宽度 */
}

/* 验证码输入容器：适配小屏幕 */
.code-input-container {
  display: flex;
  align-items: center;
  width: 100%;
}

/* 按钮区域样式 */
.form-actions {
  margin-top: 15px;
}

/* 模式切换文本样式 */
.mode-switch-text {
  text-align: center;
  margin-top: 15px;
  color: #666;
  font-size: 14px;
}

/* 重写Element Plus样式：按钮主色调 */
:deep(.el-button--primary) {
  background-color: #bdb8f9;
  border-color: #bdb8f9;
  border-radius: 6px;
  height: 44px;
  font-size: 16px;
}

:deep(.el-button--primary:hover),
:deep(.el-button--primary:focus) {
  background-color: #bdb8f9;
  border-color: #bdb8f9;
}

/* 重写输入框样式：统一风格 */
:deep(.el-input__wrapper) {
  border-radius: 6px;
  box-shadow: none;
  height: 44px;
}

:deep(.el-input__inner) {
  font-size: 15px;
}

/* 重写表单校验提示样式 */
:deep(.el-form-item__error) {
  font-size: 12px;
}

/* 重写文本按钮样式 */
:deep(.el-button--text) {
  color: #a8c7da;
  font-size: 14px;
}

:deep(.el-button--text:hover) {
  color: #a8c7da;
}

/* 媒体查询：小屏幕适配（手机端） */
@media (max-width: 375px) {
  .right-section {
    padding: 30px 20px;
  }

  .form-title {
    font-size: 20px;
  }

  :deep(.el-input__wrapper),
  :deep(.el-button--primary) {
    height: 40px;
  }

  .code-input-container {
    flex-direction: column;
    gap: 10px;
  }

  .code-input-container .el-input {
    width: 100% !important;
  }

  .code-input-container .el-button {
    width: 100%;
    margin-left: 0 !important;
  }
}
</style>