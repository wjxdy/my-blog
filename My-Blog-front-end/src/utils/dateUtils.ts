/**
 * 格式化ISO日期字符串为友好的显示格式
 * @param dateString ISO格式的日期字符串（如：2025-10-14T23:32:03）
 * @returns 格式化后的日期字符串（如：2025-10-14 23:32:03）
 */
export const formatDate = (dateString: string): string => {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');
    
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
};

/**
 * 格式化ISO日期字符串为简短的显示格式（不含秒）
 * @param dateString ISO格式的日期字符串（如：2025-10-14T23:32:03）
 * @returns 格式化后的日期字符串（如：2025-10-14 23:32）
 */
export const formatDateShort = (dateString: string): string => {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    
    return `${year}-${month}-${day} ${hours}:${minutes}`;
};