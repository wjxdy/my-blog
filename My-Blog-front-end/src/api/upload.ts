import request from '@/utils/request'
import type { ApiResponse } from "@/types/api";

/**
 * 上传图片
 * @param file 图片文件
 * @returns 图片URL
 */
export const uploadImageApi = (file: File): Promise<ApiResponse> => {
    const formData = new FormData()
    formData.append('file', file)
    return request({
        url: '/upload/image',
        method: 'post',
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}

/**
 * 上传头像
 * @param file 头像图片
 * @returns 头像URL
 */
export const uploadAvatarApi = (file: File): Promise<ApiResponse> => {
    const formData = new FormData()
    formData.append('file', file)
    return request({
        url: '/upload/avatar',
        method: 'post',
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}

/**
 * 通用文件上传（支持音乐等）
 * @param file 文件
 * @returns 文件URL
 */
export const uploadFileApi = (file: File): Promise<ApiResponse> => {
    const formData = new FormData()
    formData.append('file', file)
    return request({
        url: '/upload/file',
        method: 'post',
        data: formData,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    })
}
