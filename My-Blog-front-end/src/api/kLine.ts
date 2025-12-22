import request from '@/utils/request'
export const fetchHistoryDataApi = () => {
    return request({
        url: `/binance`,  // 拼接路径参数
        method: 'get'
    })
}