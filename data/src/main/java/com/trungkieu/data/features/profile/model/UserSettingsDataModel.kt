package com.trungkieu.data.features.profile.model

import com.trungkieu.data.features.profile.datastore.UserSettingsProto

data class UserSettingsDataModel(
    val useSystemTheme: Boolean,
    val useDarkTheme: Boolean,
    val useAutoGc: Boolean,
    val showOnBoarding: Boolean
)

fun UserSettingsProto.toDataModel() = UserSettingsDataModel(
    useSystemTheme = useCustomTheme.not(),
    useDarkTheme = useDarkTheme,
    useAutoGc = useAutoGc,
    showOnBoarding = onboardingWasShown.not()
)

fun UserSettingsDataModel.toDomain() = UserSettings(
    isUseSystemTheme = useSystemTheme,
    isDarkMode = useDarkTheme,
    isAutoGcEnabled = useAutoGc,
    isShowOnBoarding = showOnBoarding
)

