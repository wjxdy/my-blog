import request from '@/utils/request'

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
export const getArticleApi = (id : String) => { 
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

