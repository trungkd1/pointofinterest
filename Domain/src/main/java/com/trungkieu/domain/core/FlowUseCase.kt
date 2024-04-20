package com.trungkieu.domain.core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

// PARAMS: Kiểu dữ liệu của tham số đầu vào.
//RESULT: Kiểu dữ liệu của kết quả đầu ra.
// CoroutineDispatcher : Là một giao diện trong Kotlin đại diện cho một bộ điều phối các tác vụ coroutine.
//Có nhiều bộ điều phối khác nhau, mỗi bộ điều phối có chức năng riêng:
// - Dispatchers.IO: Dùng cho các thao tác I/O (như đọc/ghi file, truy cập mạng).
// - Dispatchers.Default: Dùng cho các thao tác CPU nặng.
// - Dispatchers.Main: Dùng cho các thao tác cập nhật giao diện người dùng.
// Dispatchers.IO : Đảm bảo các thao tác I/O được thực hiện trên các luồng riêng biệt, không chặn luồng chính của ứng dụng.
//Tăng hiệu suất và khả năng phản hồi của ứng dụng.
abstract class FlowUseCase<PARAMS, RESULT>(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {
    // Hàm trừu tượng operation với tham số đầu vào Params và trả kết quả là dạng Flow<Result>
    abstract fun operation(params: PARAMS): Flow<RESULT>

    // Có thể gọi use case trực tiếp như một hàm nhờ hàm invoke: kiểu getUserSettingsUseCase(Unit) thay vì getUserSettingsUseCase.operation(Unit)
    operator fun invoke(params: PARAMS): Flow<RESULT> = operation(params).flowOn(dispatcher)
}