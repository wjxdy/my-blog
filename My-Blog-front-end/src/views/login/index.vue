<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage } from 'element-plus'
import { userLoginApi } from '@/api/user'
import router from '@/router'
import { useUserInfoStore } from '@/stores/counter'

// 表单数据
const loginForm = reactive({
  username: "",
  password: ""
})

// 表单引用
const ruleFormRef = ref<FormInstance>()

// 表单校验规则
const rules = reactive<FormRules>({
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
    { min: 3, max: 10, message: '账号长度在 3 到 10 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 3, message: '密码长度不能少于 3 个字符', trigger: 'blur' }
  ]
})

// 提交表单
const submitForm = async (formEl: FormInstance | undefined) => {


  // 执行表单校验后编写

  // 校验通过，执行登录逻辑
  //登录校验逻辑处！！！
  const result = await userLoginApi(loginForm)
  if (result.code == 200) {
    localStorage.setItem('userInfo', JSON.stringify(result.data))

    //保存信息到pinia中
    const userStore = useUserInfoStore()
    
    userStore.loginData = result.data
    
    

    ElMessage.success("登录成功，正在跳转...")
    router.push("/")
  } else {

    ElMessage.error("账号或密码错误")

  }
 


}

// 重置表单
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}



</script>


<template>


  <!-- 外层容器实现居中 -->
  <div class="login-container">
    <div class="login-card">
      <h2 class="login-title">my-blog</h2>
      <el-form ref="ruleFormRef" :model="loginForm" :rules="rules" status-icon label-width="80px" class="login-form">
        <el-form-item label="账号" prop="username">
          <el-input v-model="loginForm.username" placeholder="请输入账号" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" autocomplete="off" />
        </el-form-item>
        <el-form-item class="form-actions">
          <el-button type="primary" @click="submitForm(ruleFormRef)">
            登录
          </el-button>
          <el-button @click="resetForm(ruleFormRef)">
            重置
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>

</template>

<style scoped>
/* 登录容器样式，实现居中 */
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f5f7fa;
  padding: 20px;

  /* 相对路径 - 图片放在src/assets目录下 */
  background-image: url('../../assets/BackgroundImage/仓储背景.png');

  /* 其他背景属性 */
  background-size: cover;
  /* 覆盖整个容器 */
  background-position: center;
  /* 居中显示 */
  background-repeat: no-repeat;
  /* 不重复 */
  min-height: 100vh;
  /* 确保占满整个视口高度 */
}

/* 登录卡片样式 */
.login-card {
  width: 100%;
  max-width: 400px;
  padding: 30px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

/* 登录标题样式 */
.login-title {
  text-align: center;
  margin-bottom: 20px;
  color: #303133;
  font-size: 20px;
  font-weight: 500;
}

/* 表单样式调整 */
.login-form {
  margin-top: 20px;
}

/* 按钮区域样式 */
.form-actions {
  display: flex;
  justify-content: center;
  gap: 15px;
  margin-top: 15px;
}
</style>
