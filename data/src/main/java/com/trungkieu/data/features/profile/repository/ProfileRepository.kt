package com.trungkieu.data.features.profile.repository

import com.trungkieu.data.features.profile.model.UserSettings
import com.trungkieu.data.features.profile.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getUserProfile(): Flow<UserProfile>
    suspend fun setUserProfile(userProfile: UserProfile)
    suspend fun deleteUserProfile()

    fun getUserSetting(): Flow<UserSettings>
    suspend fun setUseSystemTheme(state: Boolean)
    suspend fun setUseDarkTheme(state: Boolean)
    suspend fun setUseAutoGc(state: Boolean)
    suspend fun setShowOnBoarding(state: Boolean)
}