export interface ApiResponse<T> {
    code: number;
    data: T;
    message?: string; // 可选的消息字段
}