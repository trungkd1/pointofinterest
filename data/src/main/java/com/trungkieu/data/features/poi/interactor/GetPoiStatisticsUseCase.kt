package com.trungkieu.domain.features.poi.interactor

import com.trungkieu.data.core.UseCase
import com.trungkieu.data.core.UseCaseDispatcher
import com.trungkieu.data.features.categories.model.Category
import com.trungkieu.data.features.categories.model.PoiCategoriesUsageStatistics
import com.trungkieu.data.features.categories.model.PoiCreationStatistics
import com.trungkieu.data.features.categories.model.PoiStatistics
import com.trungkieu.data.features.categories.model.PoiStatisticsSnapshot
import com.trungkieu.data.features.categories.model.PoiViewsStatistics
import com.trungkieu.data.features.categories.repository.CategoriesRepository
import com.trungkieu.data.features.poi.repository.PoiRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class GetPoiStatisticsUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository,
    private val repository: PoiRepository,
    @UseCaseDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<Unit, PoiStatistics>(dispatcher) {

    override suspend fun operation(params: Unit): PoiStatistics {
        val snapshot = repository.getStatistics()
        val usedCategories = categoriesRepository.getCategories(snapshot.categoriesUsageCount.keys.map { it.toInt() }).single()
        return snapshot toStatisticsWith usedCategories
    }

    private infix fun PoiStatisticsSnapshot.toStatisticsWith(usedCategories: List<Category>): PoiStatistics {
        val categoriesUsage = hashMapOf<Category, Int>()
        categoriesUsageCount.forEach { (key, value) -> categoriesUsage[usedCategories.find { it.id == key }!!] = value }
        return PoiStatistics(
            viewsStatistics = PoiViewsStatistics(viewedPoiCount, unViewedPoiCount),
            categoriesUsageStatistics = PoiCategoriesUsageStatistics(categoriesUsage),
            creationStatistics = PoiCreationStatistics(poiAdditionsHistory)
        )
    }
}