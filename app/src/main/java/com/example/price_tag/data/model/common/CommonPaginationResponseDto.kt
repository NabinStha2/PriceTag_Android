package com.example.price_tag.data.model.common

import kotlinx.serialization.Serializable

@Serializable
data class CommonPaginationResponseDto(
    val currentPage: Long? = null,
    val pageSize: Long? = null,
    val totalItems: Long? = null,
    val totalPages: Long? = null,
    val hasNext: Boolean? = null,
    val hasPrevious: Boolean? = null,
)