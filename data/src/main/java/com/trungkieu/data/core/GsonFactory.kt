package com.trungkieu.data.core

import com.google.gson.Gson
import com.google.gson.GsonBuilder

// viết kiểu object sẽ ngắn gọn hơn dùng class GsonFactory\
// class GsonFactory private constructor() {
//
//    companion object {
//        private val instance = GsonBuilder()
//            .setLenient()
//            .create()
//
//        fun create(): Gson {
//            return instance
//        }
//    }
//}
// code sẽ dài hơn\
// GsonFactory dùng để
// Serializing: Chuyển đổi các đối tượng Java sang định dạng JSON.
// Deserializing: Chuyển đổi dữ liệu JSON sang các đối tượng Java.
object GsonFactory {

    fun create(): Gson = GsonBuilder()
        .setLenient()
        .create()
}