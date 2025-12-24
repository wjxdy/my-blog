export interface ApiResponse<T> {
    code: number;
    data: T;
    message?: string; // 可选的消息字段
}
// 定义分页信息接口
export interface PageInfo {
    pageSize: number;
    pageTotal: number;
    currentPage: number;
    condition: string;
    tags: Tag[];
}

export interface Tag {
    articleTagId: string;
    articleTagName: string;
}

// 定义文章类型接口
export interface Article {
    articleId: number;
    articleTitle: string;
    username: string;
    articleAddTime: string;
    articleIntro: string;
}

// 定义分页数据接口
export interface ArticlePageData {
    data: Article[];
    pageTotal: number;
}
export interface Result {
    data: any;
    message: string;
    code: number;
}