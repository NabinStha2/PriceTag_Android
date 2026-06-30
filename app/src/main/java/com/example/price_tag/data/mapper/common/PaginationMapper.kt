package com.example.price_tag.data.mapper.common

import com.example.price_tag.data.model.common.CommonPaginationResponseDto
import com.example.price_tag.domain.model.common.PaginationModel

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