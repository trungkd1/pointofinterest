package com.trungkieu.data.core

import okhttp3.Authenticator
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit
object OkHttpClientFactory {
    private const val DEFAULT_TIME_OUT = 30L

    private const val CONNECTION_TIMEOUT: Long = DEFAULT_TIME_OUT
    private const val READ_TIMEOUT: Long = DEFAULT_TIME_OUT
    private const val WRITE_TIMEOUT: Long = DEFAULT_TIME_OUT

    private const val CACHE_SIZE: Long = 1024 * 1024 * 10 // 10Mb

    @Suppress("LongMethod")
    fun create(
        // Bộ nhớ cache: OkHttpClient sẽ lưu trữ các phản hồi HTTP
        cacheFolder: File? = null,
        // Interceptor là một thành phần cho phép bạn can thiệp vào quá trình
        // thực hiện request (yêu cầu) và response (phản hồi) của các request HTTP được thực hiện bởi OkHttp.
        // vd : Bạn có thể sử dụng interceptor để thêm các header tùy chỉnh vào request HTTP trước khi gửi nó đến máy chủ.
        @Interceptors interceptors: List<Interceptor>,
        @NetworkInterceptors networkInterceptors: List<Interceptor>,
        authenticator: Authenticator? = null
    ): OkHttpClient =
        OkHttpClient().newBuilder()
            .apply {
                if (cacheFolder != null) {
                    cache(Cache(cacheFolder, CACHE_SIZE))
                }

                for (interceptor in interceptors) {
                    addInterceptor(interceptor)
                }

                for (interceptor in networkInterceptors) {
                    addNetworkInterceptor(interceptor)
                }

                connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                followRedirects(true)
                followSslRedirects(true)
                if (authenticator != null) {
                    authenticator(authenticator)
                }
            }
            .build()

    fun create(
        @Interceptors interceptors: List<Interceptor>,
        @NetworkInterceptors networkInterceptors: List<Interceptor>
    ): OkHttpClient = create(
        cacheFolder = null,
        interceptors = interceptors,
        networkInterceptors = networkInterceptors,
        authenticator = null
    )
}
