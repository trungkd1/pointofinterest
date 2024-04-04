package com.trungkieu.data.features.categories.model


data class PoiCreationPayload(
    val contentLink: String?,
    val title: String,
    val body: String?,
    val imageUrl: String?,
    val categories: List<Category>
)