package com.example.price_tag.core.common.data.mapper

import com.example.price_tag.core.common.data.model.CommonPaginationResponseDto
import com.example.price_tag.core.common.domain.model.PaginationModel

fun CommonPaginationResponseDto.toDomain(): PaginationModel {
    return PaginationModel(
        currentPage = currentPage?.toInt() ?: 0,
        pageSize = pageSize?.toInt() ?: 0,
        totalItems = totalItems ?: 0L,
        totalPages = totalPages?.toInt() ?: 0,
        hasNext = hasNext ?: false,
        hasPrevious = hasPrevious ?: false,
    )
}