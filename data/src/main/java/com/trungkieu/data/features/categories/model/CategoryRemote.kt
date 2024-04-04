package com.trungkieu.data.features.categories.model

import android.graphics.Color
import androidx.annotation.VisibleForTesting
import com.google.gson.annotations.SerializedName
import com.trungkieu.data.core.UNSPECIFIED_ID

data class CategoryRemote(
    @SerializedName("title") val title: String,
    @SerializedName("color") val color: String,
    @SerializedName("type") val type: String
)

fun CategoryRemote.toDataModel() = CategoryDataModel(
    id = UNSPECIFIED_ID,
    title = title,
    color = color.toColorInt(),
    type = type,
    isMutable = false
)

@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
fun String.toColorInt() = Color.parseColor(this)