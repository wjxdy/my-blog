import request from '@/utils/request'
export const getTagListApi = () => {
    return request({
        url: '/tag',
        method: 'get'
    })
}

export const addTagApi = (params: string) => { 
    return request({
        url: '/tag/add',
        method: 'get',
        params: { tagName: params }
    })
}

export const deleteTagApi = (tagId: string) => { 
    return request({
        url: `/tag/delete/${tagId}`,
        method: 'delete'
    })
}