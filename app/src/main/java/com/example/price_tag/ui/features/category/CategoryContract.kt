package com.example.price_tag.ui.features.category

import com.example.price_tag.domain.model.category.Category
import com.example.price_tag.domain.model.category.SubCategory
import com.example.utilites.ResourceState

class CategoryContract {

    sealed interface Intent {
        data object LoadCategories : Intent
        data class SelectCategory(val category: Category, val index: Int) : Intent
        data class SubCategoryClick(val subCategoryId: Long) : Intent
    }

    data class State(
        val categories: ResourceState<List<Category>> = ResourceState.Initial(),
        val subCategories: ResourceState<List<SubCategory>> = ResourceState.Initial(),
        val selectedIndex: Int = 0,
        val selectedCategoryId: Long? = null
    )

    sealed interface Effect {
        data class NavigateToProducts(val subCategoryId: Long) : Effect
    }
}
