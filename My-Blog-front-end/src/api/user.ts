import request from '@/utils/request'


export const userLoginApi = (params : any) => { 

    return request({
        url: "/user/login",  
        method: 'post',
        data:params
    })
}