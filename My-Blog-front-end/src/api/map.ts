import request from '@/utils/request'
export const newsApi = (params:any)=> {
  return request({
    url: "/news",
    method: "post",
    data: params,
  });
};
