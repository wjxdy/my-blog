import request from '@/utils/request'
import type { ApiResponse } from "@/types/api";

export const deleteArticleApi = (articleId : string) => { 

    return request({
        url: `/article/deleteArticle/${articleId}`,  // 拼接路径参数
        method: 'delete'  
    })
}
export const updateArticleApi = (params : any) => { 

    return request({
        url:'/article/updateArticle',
        method: 'put',
        data: params
    })
}
export const getArticleListApi = ( ) => { 

    return request({
        url:'/article',
        method:'get'
    })
}
export const getArticlePageApi = (params : any) => { 

    return request({
        url:'/article/list',
        method: 'post',
        data: params
    })
}
export const getArticleApi = (id : string) => { 
    return request({
        url:`/article/${id}`,
        method: 'get' 
    })
}

export const addArticleApi = (params : any) => { 

    return request({
        url:'/article/add',
        method: 'post',
        data: params
    })
}

