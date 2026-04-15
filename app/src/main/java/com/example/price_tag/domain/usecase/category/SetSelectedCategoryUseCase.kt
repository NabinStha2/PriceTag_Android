package com.example.price_tag.domain.usecase.category

import com.example.price_tag.domain.repository.category.CategoryRepository
import javax.inject.Inject

class SetSelectedCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    operator fun invoke(name: String) {
        repository.setSelectedCategory(name)
    }
}
