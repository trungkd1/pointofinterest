package com.trungkieu.data.database

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

// Trong Android Room, khi bạn sử dụng các kiểu dữ liệu không được Room hỗ trợ mặc định
// (ví dụ như Instant trong trường hợp này), bạn cần phải cung cấp các converter để Room có thể hiểu và lưu trữ dữ liệu đó.
// kiểu dữ liệu Instant : vd "2024-04-01T12:00:00Z"
// kiểu dữ liệu long : 1658251200000
class InstantConverter {
    @TypeConverter
    fun longToInstant(value: Long?): Instant? =
        value?.let(Instant::fromEpochMilliseconds)

    @TypeConverter
    fun instantToLong(instant: Instant?): Long? =
        instant?.toEpochMilliseconds()
}