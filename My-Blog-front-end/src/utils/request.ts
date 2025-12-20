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
    if(error.response.status===401){
        
        ElMessage.error('登录身份信息失效，请重新登录')
        localStorage.removeItem('userInfo');
        router.push('/login')
    }else{
      
    }
    

    return Promise.reject(error)
  }
)

export default request