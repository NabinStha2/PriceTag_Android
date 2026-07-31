package com.example.price_tag.feature.category.data.mapper

import com.example.price_tag.feature.category.data.model.CategoryData
import com.example.price_tag.feature.category.data.model.SubCategoryResult
import com.example.price_tag.feature.category.domain.model.Category
import com.example.price_tag.feature.category.domain.model.SubCategory

fun CategoryData.toDomain(): Category {
    return Category(
        id = id ?: 0L,
        name = name ?: "",
        imageUrl = imageUrl,
    )
}

fun SubCategoryResult.toDomain(): SubCategory {
    return SubCategory(
        id = id ?: 0L,
        name = subCategoryName ?: "",
        categoryId = categoryId ?: 0L,
        imageUrl = imageUrl,
    )
}
