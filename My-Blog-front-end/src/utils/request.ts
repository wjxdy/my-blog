import router from '@/router'
import axios from 'axios'
import { ElMessage } from 'element-plus'

//创建axios实例对象
const request = axios.create({
  baseURL: '/api',
  timeout: 600000
})

// 检查token是否过期
const isTokenExpired = (token: string): boolean => {
    try {
        // JWT token 格式: header.payload.signature
        const parts = token.split('.')
        if (parts.length !== 3) return true
        
        // 解析 payload (Base64)
        const payload = JSON.parse(atob(parts[1]))
        
        // 检查 exp (过期时间戳，秒级)
        if (payload.exp) {
            const now = Math.floor(Date.now() / 1000)
            return payload.exp < now
        }
        return false
    } catch (e) {
        console.error('Token解析失败:', e)
        return true
    }
}

// 清除登录状态
const clearLoginState = () => {
    localStorage.removeItem('userInfo')
    ElMessage.error('登录已过期，请重新登录')
    router.push('/login')
}

request.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    let userInfo = localStorage.getItem('userInfo')
    
    if (userInfo) {
        try {
            const parsed = JSON.parse(userInfo)
            const token = parsed.token
            
            // 检查token是否过期
            if (token && isTokenExpired(token)) {
                console.log('请求拦截器 - token已过期')
                clearLoginState()
                return Promise.reject(new Error('Token已过期'))
            }
            
            config.headers.token = token
            console.log('请求拦截器 - 设置token, URL:', config.url)
        } catch (e) {
            console.error('解析userInfo失败:', e)
            localStorage.removeItem('userInfo')
        }
    } else {
        console.log('请求拦截器 - 未找到userInfo, URL:', config.url)
    }
    return config;
  }, function (error) {
    // 对请求错误做些什么
    return Promise.reject(error);
  });



//axios的响应 response 拦截器
request.interceptors.response.use(
  (response) => { //成功回调
    return response.data
  },
  (error) => { //失败回调
    const requestPath = error.config?.url || ''
    
    // 处理401未授权（token无效或过期）
    if (error.response?.status === 401) {
        // 清除登录状态
        localStorage.removeItem('userInfo')
        ElMessage.error('登录已过期，请重新登录')
        router.push('/login')
    }
    
    return Promise.reject(error)
  }
)

export default request