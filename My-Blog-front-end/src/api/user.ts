import request from "@/utils/request";

// 定义统一的API响应类型
interface ApiResponse<T = any> {
  code: string;
  message: string;
  data?: T;
}

// 定义登录响应数据类型
interface LoginData {
  sex: string;
  id: string;
  name: string;
  token: string;
  username: string;
}

// 用户登录
export const userLoginApi = (params: { username: string; password: string }): Promise<ApiResponse<LoginData>> => {
  return request({
    url: "/user/login",
    method: "post",
    data: params,
  });
};

// 发送邮箱验证码
export const sendEmailCodeApi = (email: string): Promise<ApiResponse> => {
  return request({
    url: `/email/sendMessage/${email}`,
    method: "get",
  });
};

// 验证邮箱验证码
export const verifyEmailCodeApi = (params: { emailAddress: string; code: string }): Promise<ApiResponse> => {
  return request({
    url: "/email/verifyCode",
    method: "post",
    data: params,
  });
};

// 用户注册
export const userRegisterApi = (params: { username: string; password: string; name: string; email: string }): Promise<ApiResponse> => {
  return request({
    url: "/user/register",
    method: "post",
    data: params,
  });
};
