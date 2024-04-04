package com.trungkieu.data.features.categories.model

import com.trungkieu.data.core.UNSPECIFIED_ID

data class CategoryDataModel(
    val id: Int,
    val title: String,
    val color: Int,
    val type: String,
    val isMutable: Boolean
) {
    companion object {
        val EMPTY = CategoryDataModel(-1, "", 0, "", false)
    }
}

fun CategoryDataModel.toDomain() = Category(
    id = id.toString(),
    title = title,
    color = color,
    categoryType = type.toCategoryType(),
    isMutable = isMutable
)

fun Category.toDataModel() = CategoryDataModel(
    id = id.toInt(),
    title = title,
    color = color,
    type = categoryType.name,
    isMutable = isMutable
)

fun CreateCategoryPayload.toDataModel() = CategoryDataModel(
    id = UNSPECIFIED_ID,
    title = title,
    color = color,
    type = CategoryType.PERSONAL.name,
    isMutable = true
)

private fun String.toCategoryType() = CategoryType.valueOf(this)