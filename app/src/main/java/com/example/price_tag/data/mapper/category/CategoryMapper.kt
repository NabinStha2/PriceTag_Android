package com.example.price_tag.data.mapper.category

import com.example.price_tag.data.model.category.CategoryData
import com.example.price_tag.data.model.category.SubCategoryResult
import com.example.price_tag.domain.model.category.Category
import com.example.price_tag.domain.model.category.SubCategory

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
