export interface ApiResponse {
    data: any;
    message: string;
    code: number; // 可选的消息字段
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
    articleId: string;
    articleTitle: string;
    username: string;
    articleAddTime: string;
    articleContext: string;  // 文章内容摘要（后端已截取前200字符）
    articleGoodNumber?: number;
    articleLookNumber?: number;
    articleCollectionNumber?: number;
    tags?: Tag[];  // 文章标签列表
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