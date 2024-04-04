package com.trungkieu.domain.features.poi.interactor

import com.trungkieu.data.core.UseCase
import com.trungkieu.data.core.UseCaseDispatcher
import com.trungkieu.data.features.categories.model.WizardSuggestion
import com.trungkieu.data.features.poi.repository.PoiRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetWizardSuggestionUseCase @Inject constructor(
    private val repository: PoiRepository,
    @UseCaseDispatcher private val dispatcher: CoroutineDispatcher
) : UseCase<GetWizardSuggestionUseCase.Params, WizardSuggestion>(dispatcher) {

    override suspend fun operation(params: Params): WizardSuggestion {
        return repository.getWizardSuggestion(params.contentUrl)
    }

    data class Params(val contentUrl: String)
}