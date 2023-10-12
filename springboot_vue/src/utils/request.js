// 通过 axios 创建对象
import axios from "axios";

const request= axios.create({
    timeout: 5000
})
// request 拦截器
// 1. 可以对请求做一些处理
// 2. 比如统一加 token，Content-Type 等
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';//设置请求头
    return config
}, error => {
    return Promise.reject(error)
});
request.interceptors.response.use(response => {
    let res = response.data; //取出返回 data 的内容
        // 如果是返回的文件
    if (response.config.responseType === 'blob') {
        return res
    }
        // 如果返回的是 string ,就转成 json 对象
    if (typeof res === 'string') {
        res = res ? JSON.parse(res) : res
    }
    return res;
},error => {
    console.log('err' + error) // 输出错误信息
    return Promise.reject(error)
})
//导出
export default request