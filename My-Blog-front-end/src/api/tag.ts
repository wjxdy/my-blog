import request from '@/utils/request'
export const getTagListApi = () => {
    return request({
        url: '/tag',
        method: 'get'
    })
}

export const addTagApi = (params: String) => { 
    return request({
        url: '/tag/add',
        method: 'post',
        data: params
    })
}

export const deleteTagApi = (tagId: String) => { 
    return request({
        url: `/tag/delete/${tagId}`,
        method: 'delete'
    })
}