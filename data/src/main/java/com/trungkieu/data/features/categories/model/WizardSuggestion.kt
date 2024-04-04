package com.trungkieu.data.features.categories.model

data class WizardSuggestion(
    val contentUrl: String,
    val title: String?,
    val body: String?,
    val imageUrl: String?,
    val tags: List<String>?
)