package com.trungkieu.data.features.poi.datasource

import com.trungkieu.data.features.poi.model.WizardSuggestionDataModel

interface WizardDataSource {

    suspend fun getWizardSuggestion(url: String): WizardSuggestionDataModel
}