package com.example.price_tag.domain.usecase.category

import com.example.price_tag.domain.repository.category.CategoryRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetSelectedCategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    operator fun invoke(): StateFlow<String?> {
        return repository.selectedCategoryName
    }
}
