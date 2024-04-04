package com.trungkieu.data.core

import javax.inject.Qualifier


// Đây là các code định nghĩa các annotation tùy chỉnh dùng để phân biệt giữa các dependency có cùng kiểu dữ liệu trong Dagger Hilt.

@Qualifier //  sử dụng @Qualifier để Dagger có thể phân biệt hai dependency có cùng kiểu dữ liệu
@MustBeDocumented // Dòng "This class is annotated with <code>@Remote</code>" sẽ tự động được sinh ra bởi công cụ Javadoc khi bạn sử dụng annotation @MustBeDocumented.
@Retention(AnnotationRetention.RUNTIME)
annotation class Remote // Được sử dụng để phân biệt các đối tượng/phương thức liên quan đến dữ liệu từ server hoặc các nguồn bên ngoài.

@Qualifier
@MustBeDocumented // Bắt buộc phần mềm xử lý annotation phải đưa annotation này vào Javadoc.
@Retention(AnnotationRetention.RUNTIME) // Giữ annotation tồn tại đến thời điểm chạy (runtime). Thời điểm chạy (runtime) là giai đoạn sau khi chương trình được biên dịch và bắt đầu thực thi. Đây là thời điểm mà chương trình được tải vào bộ nhớ và các lệnh trong chương trình được thực hiện từng bước.
annotation class Local // Được sử dụng để phân biệt các đối tượng/phương thức liên quan đến dữ liệu cục bộ trên thiết bị.

@Qualifier // Các annotation có @Qualifier phải là dạng duy nhất để đảm bảo Dagger Hilt có thể phân biệt được các dependency.
@MustBeDocumented // Bắt buộc phần mềm xử lý annotation phải đưa annotation này vào Javadoc.
@Retention(AnnotationRetention.RUNTIME) // Giữ annotation tồn tại đến thời điểm chạy (runtime). Thời điểm chạy (runtime) là giai đoạn sau khi chương trình được biên dịch và bắt đầu thực thi. Đây là thời điểm mà chương trình được tải vào bộ nhớ và các lệnh trong chương trình được thực hiện từng bước.
annotation class Interceptors

@Qualifier // Các annotation có @Qualifier phải là dạng duy nhất để đảm bảo Dagger Hilt có thể phân biệt được các dependency.
@MustBeDocumented // Bắt buộc phần mềm xử lý annotation phải đưa annotation này vào Javadoc.
@Retention(AnnotationRetention.RUNTIME) // Giữ annotation tồn tại đến thời điểm chạy (runtime). Thời điểm chạy (runtime) là giai đoạn sau khi chương trình được biên dịch và bắt đầu thực thi. Đây là thời điểm mà chương trình được tải vào bộ nhớ và các lệnh trong chương trình được thực hiện từng bước.
annotation class NetworkInterceptors

@Qualifier // Các annotation có @Qualifier phải là dạng duy nhất để đảm bảo Dagger Hilt có thể phân biệt được các dependency.
@MustBeDocumented // Bắt buộc phần mềm xử lý annotation phải đưa annotation này vào Javadoc.
@Retention(AnnotationRetention.RUNTIME) // Giữ annotation tồn tại đến thời điểm chạy (runtime). Thời điểm chạy (runtime) là giai đoạn sau khi chương trình được biên dịch và bắt đầu thực thi. Đây là thời điểm mà chương trình được tải vào bộ nhớ và các lệnh trong chương trình được thực hiện từng bước.
annotation class ServerUrl

@Qualifier // Các annotation có @Qualifier phải là dạng duy nhất để đảm bảo Dagger Hilt có thể phân biệt được các dependency.
@MustBeDocumented // Bắt buộc phần mềm xử lý annotation phải đưa annotation này vào Javadoc.
@Retention(AnnotationRetention.RUNTIME) // Giữ annotation tồn tại đến thời điểm chạy (runtime). Thời điểm chạy (runtime) là giai đoạn sau khi chương trình được biên dịch và bắt đầu thực thi. Đây là thời điểm mà chương trình được tải vào bộ nhớ và các lệnh trong chương trình được thực hiện từng bước.
annotation class UserSettings

@Qualifier // Các annotation có @Qualifier phải là dạng duy nhất để đảm bảo Dagger Hilt có thể phân biệt được các dependency.
@MustBeDocumented // Bắt buộc phần mềm xử lý annotation phải đưa annotation này vào Javadoc.
@Retention(AnnotationRetention.RUNTIME) // Giữ annotation tồn tại đến thời điểm chạy (runtime). Thời điểm chạy (runtime) là giai đoạn sau khi chương trình được biên dịch và bắt đầu thực thi. Đây là thời điểm mà chương trình được tải vào bộ nhớ và các lệnh trong chương trình được thực hiện từng bước.
annotation class UserProfile

@Qualifier
@MustBeDocumented // Bắt buộc phần mềm xử lý annotation phải đưa annotation này vào Javadoc.
@Retention(AnnotationRetention.RUNTIME) // Giữ annotation tồn tại đến thời điểm chạy (runtime). Thời điểm chạy (runtime) là giai đoạn sau khi chương trình được biên dịch và bắt đầu thực thi. Đây là thời điểm mà chương trình được tải vào bộ nhớ và các lệnh trong chương trình được thực hiện từng bước.
annotation class CacheFolder

@Qualifier
@MustBeDocumented // Bắt buộc phần mềm xử lý annotation phải đưa annotation này vào Javadoc.
@Retention(AnnotationRetention.RUNTIME) // Giữ annotation tồn tại đến thời điểm chạy (runtime). Thời điểm chạy (runtime) là giai đoạn sau khi chương trình được biên dịch và bắt đầu thực thi. Đây là thời điểm mà chương trình được tải vào bộ nhớ và các lệnh trong chương trình được thực hiện từng bước.
annotation class FakeRemote

@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class UseCaseDispatcher