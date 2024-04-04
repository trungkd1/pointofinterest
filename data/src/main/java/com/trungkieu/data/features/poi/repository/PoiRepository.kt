package com.trungkieu.data.features.poi.repository

import com.trungkieu.data.features.categories.model.*
import kotlinx.coroutines.flow.Flow

/**
 * Poi repository :
 *
 * @constructor Create empty Poi repository
 */
interface PoiRepository {

    fun getPoiList(sortOption: PoiSortOption?): Flow<List<PoiModel>>

    fun getUsedCategories(): Flow<List<Int>>

    suspend fun searchPoi(query: String): List<PoiModel>

    suspend fun getDetailedPoi(id: String): PoiModel

    suspend fun createPoi(payload: PoiCreationPayload)

    suspend fun deletePoi(model: PoiModel)

    suspend fun deleteGarbage(): Int

    suspend fun addComment(targetId: String, payload: PoiCommentPayload)

    fun getComments(targetId: String): Flow<List<PoiComment>>

    suspend fun deleteComment(id: String)

    suspend fun getWizardSuggestion(contentUrl: String): WizardSuggestion

    suspend fun getStatistics(): PoiStatisticsSnapshot
}