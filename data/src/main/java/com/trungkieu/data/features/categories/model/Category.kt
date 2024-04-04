package com.trungkieu.data.features.categories.model

data class Category(
    val id: String,
    val title: String,
    val color: Int,
    val isMutable: Boolean,
    val categoryType: CategoryType
)

enum class CategoryType {
    SEVERITY, GLOBAL, PERSONAL
}

const val SEVERITY_HIGH = "High"
const val SEVERITY_MEDIUM = "Medium"
const val SEVERITY_NORMAL = "Normal"
const val SEVERITY_LOW = "Low"