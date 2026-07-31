package com.example.price_tag.feature.category.domain.usecase

import com.example.price_tag.feature.category.domain.repository.CategoryRepository
import javax.inject.Inject

class SetSelectedCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    operator fun invoke(name: String) {
        repository.setSelectedCategory(name)
    }
}
