package com.trungkieu.pointofinterest.features.categories.ui.models

import androidx.annotation.StringRes
import com.trungkieu.pointofinterest.R

enum class CategoryAction(@StringRes val title: Int) {
    DELETE(R.string.delete), EDIT(R.string.edit)
}