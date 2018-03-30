package com.mvp.sample.app.client

/**
 * 作者：秦川小将
 * 时间：2018/3/28
 * 描述：
 */
class ResultCode {

    /**
     * HTTP的状态码
     */
    class Http {
        companion object {
            // 请求未授权
            const val UNAUTHORIZED = 401
            // 服务器拒绝请求
            const val FORBIDDEN = 403
            // 未找到
            const val NOT_FOUND = 404
            // 请求超时
            const val REQUEST_TIMEOUT = 408
            // 服务器错误
            const val INTERNAL_SERVER_ERROR = 500
            // 错误的网关
            const val BAD_GATEWAY = 502
            // 服务无法获得
            const val SERVICE_UNAVAILABLE = 503
            // 网关超时
            const val GATEWAY_TIMEOUT = 504
        }
    }

    /**
     * Request请求码
     */
    class Request {
        companion object {
            // 未知错误
            const val UNKNOWN = -1
            // 解析错误
            const val PARSE_ERROR = 1001
            // 网络错误
            const val NETWORK_ERROR = 1002
            // 协议出错
            const val HTTP_ERROR = 1003
            // 证书出错
            const val SSL_ERROR = 1005
            // 连接超时
            const val TIMEOUT_ERROR = 1006
            // 调用错误
            const val INVOKE_ERROR = 1007
            // 类转换错误
            const val CONVERT_ERROR = 1008
            // 参数值异常
            const val ILLEGAL_ERROR = 1009
        }
    }

    /**
     * Response响应码
     */
    class Response {
        companion object {
            // 成功
            const val HTTP_SUCCESS = 0
            // 服务器返回具体错误信息
            const val SERVICE_ERROR = 1
        }
    }
}