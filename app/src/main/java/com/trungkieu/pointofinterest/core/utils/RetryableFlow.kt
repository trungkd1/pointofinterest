package com.trungkieu.pointofinterest.core.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

/**
 * Retryable flow
 *
 * @param retryTrigger : dùng để điều khiển logic thử lại.
 * @param flowProvider : Một hàm trả về , có dạng là "() -> Flow<T>"
 * @param T : có thể dùng với bất cứ nguồn dữ liệu nào từ int, String...
 * @receiver
 */// Đoạn mã này xác định một hàm có tên retryableFlow và một lớp có tên RetryTrigger
// phối hợp với nhau để triển khai cơ chế thử lại cho các luồng Kotlin.
@OptIn(ExperimentalCoroutinesApi::class)
@FlowPreview
// retryableFlowv sẽ nhận giá trị true hoặc false, tuỳ thuộc vào giá trị được phát ra bởi retryEvent
fun <Int > retryableFlow(retryTrigger: RetryTrigger, flowProvider: () -> Flow<Int>)  =
    // sử dụng flatMapLatest trong retryableFlow đảm bảo rằng chỉ có một phiên bản của flowProvider() được thực thi tại bất kỳ thời điểm nào.
    // dùng flatMapLatest để tạo ra một flow duy nhất, hạn chế lãng phí tài nguyên
    retryTrigger.retryEvent.flatMapLatest { flowProvider() }

fun retryableFlow2(retryTrigger: RetryTrigger, flowProvider: () -> Flow<Int>) : Flow<Int> {
    return retryTrigger.retryEvent.flatMapLatest { bc ->
        flowProvider() }
}
    // sử dụng flatMapLatest trong retryableFlow đảm bảo rằng chỉ có một phiên bản của flowProvider() được thực thi tại bất kỳ thời điểm nào.


class RetryTrigger {
    internal val retryEvent = MutableStateFlow(true)
    fun retry() {
        retryEvent.value = retryEvent.value.not()
    }
}