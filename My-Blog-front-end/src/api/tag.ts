import request from '@/utils/request'
import type { ApiResponse } from "@/types/api";
export const getTagListApi = (): Promise<ApiResponse> => {
    return request({
        url: '/tag',
        method: 'get'
    })
}

export const addTagApi = (params: string): Promise<ApiResponse> => { 
    return request({
        url: '/tag/add',
        method: 'get',
        params: { tagName: params }
    })
}

export const deleteTagApi = (tagId: string): Promise<ApiResponse> => { 
    return request({
        url: `/tag/delete/${tagId}`,
        method: 'delete'
    })
}