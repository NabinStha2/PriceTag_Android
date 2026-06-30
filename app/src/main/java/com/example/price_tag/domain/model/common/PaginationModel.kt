package com.example.price_tag.domain.model.common

data class PaginationModel(
    val currentPage: Int,
    val pageSize: Int,
    val totalItems: Long,
    val totalPages: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean,
)