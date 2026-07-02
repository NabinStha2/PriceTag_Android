package com.example.price_tag.domain.model.product

import com.example.price_tag.domain.model.category.Category
import com.example.price_tag.domain.model.category.SubCategory

data class SingleProductDetails(
    val id: Long,
    val name: String,
    val slug: String,
    val description: String?,
    val shortDescription: String?,
    val primaryImageURL: String?,
    val imagesURL: List<String>?,
    val basePrice: Double,
    val discountedPrice: Double,
    val brand: String?,
    val totalRating: Double,
    val totalReview: Long,
    val category: Category?,
    val subCategory: SubCategory?,
    val variants: List<ProductVariants>?,
    val variantsColorSizeListMap: Map<String, List<VariantColorSizeListMap>>? = null,
    val colorSizeVariantGroup: List<ColorSizeVariantGroup>? = null,
)

data class ColorSizeVariantGroup(
    val colorHex: String,
    val variants: List<ProductVariants>,
)

data class VariantColorSizeListMap(
    val colorId: Int?,
    val stockQuantity: Long?,
    val actualPrice: Double?,
    val discountedPrice: Double?,
    val weightInGrams: Long?,
    val sizeId: Int?,
    val sizeName: String?,
    val colorHexCode: String?,
    val primaryImageURL: String?,
    val images: List<String>?,
    val colorName: String?,
    val sizeType: String?,
    val displayOrder: Long?,
)

data class ProductVariants(
    val id: Long,
    val sku: String?,
    val stockQuantity: Long,
    val actualPrice: Double,
    val discountedPrice: Double,
    val weightInGrams: Long,
    val color: ColorVariant?,
    val size: SizeVariant?,
    val primaryImageURL: String?,
    val images: List<String>?,
)

data class ColorVariant(
    val id: Long,
    val name: String?,
    val hexCode: String?,
)

data class SizeVariant(
    val id: Long,
    val name: String?,
    val displayOrder: Long?,
    val sizeType: String?,
)