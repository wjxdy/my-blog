import router from '@/router'
import axios from 'axios'
import { ElMessage } from 'element-plus'

//创建axios实例对象
const request = axios.create({
  baseURL: '/api',
  timeout: 600000
})
request.interceptors.request.use(function (config) {
    // 在发送请求之前做些什么
    let userInfo= localStorage.getItem('userInfo')
    
    
    if(userInfo){
      let token= JSON.parse(userInfo).token
      config.headers.token=token
      console.log('请求拦截器 - 设置token, URL:', config.url)
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
    // 只有访问需要登录的接口时401才跳转登录页
    const needAuthPaths = ['/user/info', '/upload', '/article/add', '/article/update']
    const requestPath = error.config?.url || ''
    const isNeedAuth = needAuthPaths.some(path => requestPath.includes(path))
    
    if(error.response?.status === 401 && isNeedAuth){
        ElMessage.error('请先登录')
        localStorage.removeItem('userInfo');
        router.push('/login')
    } else if (error.response?.status === 401) {
        // 游客访问，不跳转，只返回错误
        console.log('游客访问无401:', requestPath)
    }
    
    return Promise.reject(error)
  }
)

export default request