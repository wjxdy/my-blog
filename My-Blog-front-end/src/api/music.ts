import request from '@/utils/request'
import type { ApiResponse } from "@/types/api";

/**
 * 获取音乐列表
 */
export const getMusicListApi = (): Promise<ApiResponse> => {
    return request({
        url: '/music',
        method: 'get'
    })
}

/**
 * 上传音乐信息
 */
export const uploadMusicApi = (params: {
    musicName: string;
    musicUrl: string;
    musicArtist?: string;
}): Promise<ApiResponse> => {
    return request({
        url: '/music/upload',
        method: 'post',
        params
    })
}

/**
 * 删除音乐
 */
export const deleteMusicApi = (musicId: string): Promise<ApiResponse> => {
    return request({
        url: `/music/${musicId}`,
        method: 'delete'
    })
}

/**
 * 获取音乐数量
 */
export const getMusicCountApi = (): Promise<ApiResponse> => {
    return request({
        url: '/music/count',
        method: 'get'
    })
}
