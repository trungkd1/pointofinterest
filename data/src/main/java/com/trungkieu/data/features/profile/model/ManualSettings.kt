package com.trungkieu.data.features.profile.model

sealed class ManualSettings {
    object UseSystemTheme : ManualSettings()
    object UseDarkTheme : ManualSettings()
    object UseAutoGc : ManualSettings()
}