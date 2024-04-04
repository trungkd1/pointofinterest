package com.trungkieu.data.di

import com.google.gson.Gson
import com.trungkieu.data.core.*
import com.trungkieu.data.core.CacheFolder
import com.trungkieu.data.core.ConverterFactory
import com.trungkieu.data.core.GsonFactory
import com.trungkieu.data.core.Interceptors
import com.trungkieu.data.core.NetworkInterceptors
import com.trungkieu.data.core.OkHttpClientFactory
import com.trungkieu.data.core.RetrofitFactory
import com.trungkieu.data.core.ServerUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.File
import javax.inject.Singleton

/**
 * Network module
 *
 * @constructor Create empty Network module
 */
@Module(includes = [InterceptorsModule::class])
@InstallIn(SingletonComponent::class)
// Trong trường hợp này, class NetworkModule không cần annotation @Inject vì nó hoạt động như một module cung cấp dependency
// chứ không phải là một class cần được inject dependency.
class NetworkModule {

    /**
     * Create gson : Gson có thể chuyển đổi các object Java sang JSON để lưu trữ hoặc truyền tải và ngược lại.
     *
     * @return
     */
    @Provides
    @Singleton
    fun createGson(): Gson = GsonFactory.create()

    /**
     * Converter factory : sử dụng cho việc phân tích cú pháp JSON.
     *
     * @param gson
     * @return
     */
    @Provides
    @Singleton
    fun converterFactory(gson: Gson): Converter.Factory = ConverterFactory.create(gson)


    /**
     * Create ok http client : OkHttp là một thư viện Android giúp thực hiện các request HTTP một cách hiệu quả và tin cậy.
     *
     * @param cacheFolder sẽ nhận giá trị được cung cấp bởi hàm provideCacheFolder() từ object CacheFolderModule
     * @param interceptors : lấy giá trị từ hàm provideInterceptors của object InterceptorsModule
     * @param networkInterceptors : lấy giá trị từ hàm provideNetworkInterceptors của object InterceptorsModule
     * @return
     */
    @Provides
    @Singleton
    fun createOkHttpClient(
        @CacheFolder cacheFolder: File,
        @Interceptors interceptors: List<Interceptor>,
        @NetworkInterceptors networkInterceptors: List<Interceptor>
    ): OkHttpClient = OkHttpClientFactory.create(
        cacheFolder,
        interceptors,
        networkInterceptors
    )

    // 1. OkHttp:
    // - Khi sử dụng OkHttp để thực hiện request API, bạn phải tự parse JSON sang các đối tượng Java.
    // - Quá trình parse JSON có thể phức tạp và tốn thời gian, đặc biệt khi JSON có cấu trúc phức tạp.
    // - Bạn cần sử dụng các thư viện JSON parser như GSON, Jackson, Moshi, v.v. để thực hiện parse JSON.
    // 2. Retrofit:
    // - Retrofit tự động chuyển đổi JSON sang các đối tượng Java dựa trên interface mà bạn khai báo.
    // - Quá trình chuyển đổi diễn ra tự động và bạn không cần phải viết code để parse JSON.
    // - Retrofit hỗ trợ nhiều định dạng dữ liệu khác nhau như XML, Jackson, GSON, Moshi, v.v.

    /**
     * retrofit
     * cung cấp giá trị retrofit cho hàm provideWizardApi ở class ApiModule
     * @param createOkHttpClient : lấy giá trị từ hàm createOkHttpClient
     * @param converterFactory334343 : lấy giá trị từ hàm converterFactory
     * @param baseUrl : lấy giá trị từ hàm provideServerUrl của class ApiConstantsModule
     * @return
     */
    @Provides
    @Singleton
    // Cung cấp một instance của Retrofit để tương tác với API, sử dụng OkHttpClient, Converter.Factory và base URL được cung cấp.
    // tên biến createOkHttpClient và converterFactory phụ thuộc vào tên hàm createOkHttpClient và converterFactory.
    // Có nghĩa là biến createOkHttpClient được tạo với kiểu dữ liệu OkHttpClient và lấy giá từ từ hàm createOkHttpClient dựa vào annotation @Provides
    //
    fun retrofit(
        createOkHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
        @ServerUrl baseUrl: String
    ): Retrofit = RetrofitFactory.create(createOkHttpClient, converterFactory, baseUrl)
}

@Module
@InstallIn(SingletonComponent::class)
object InterceptorsModule {
    // Nếu cả 2 function có củng 1 kiểu dữ liệu trả vê ,thì phải dùng annotation cho từng function để dagger có hể phân biệt
    @Provides
    @Interceptors
    @Singleton
    fun provideInterceptors(): List<@JvmWildcard Interceptor> = arrayListOf(HttpLoggingInterceptor())

    @Provides
    @NetworkInterceptors
    @Singleton
    fun provideNetworkInterceptors(): List<@JvmWildcard Interceptor> = emptyList()
}