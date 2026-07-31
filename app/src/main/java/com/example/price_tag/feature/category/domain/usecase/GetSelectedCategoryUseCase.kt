package com.example.price_tag.feature.category.domain.usecase

import com.example.price_tag.feature.category.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetSelectedCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    operator fun invoke(): StateFlow<String?> {
        return repository.selectedCategoryName
    }
}
