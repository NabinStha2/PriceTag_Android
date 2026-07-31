package com.example.price_tag.feature.product.data.model // To parse the JSON, install kotlin's serialization plugin and do:
//
// val json                            = Json { allowStructuredMapKeys = true }
// val singleProductDetailsResponseDto = json.parse(SingleProductDetailsResponseDto.serializer(), jsonString)


import com.example.price_tag.feature.category.data.model.CategoryData
import com.example.price_tag.feature.category.data.model.SubCategoryResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement

@Serializable
data class SingleProductDetailsResponseDto(
    val message: String? = null,
    val data: SingleProductDetailsDataDto? = null,
    val success: Boolean? = null,
    val status: Long? = null,
)

@Serializable
data class SingleProductDetailsDataDto(
    val id: Long? = null,
    val name: String? = null,
    val slug: String? = null,
    val description: String? = null,
    val shortDescription: String? = null,

    @SerialName("primaryImageUrl") val primaryImageURL: String? = null,

    @SerialName("imageUrl") val imageURL: JsonArray? = null,

    val basePrice: Double? = null,
    val discountedPrice: Double? = null,
    val brand: String? = null,
    val totalRating: Double? = null,
    val totalReview: Long? = null,
    val category: CategoryData? = null,
    val subCategory: SubCategoryResult? = null,
    val variants: List<ProductVariantsDto>? = null,
)

@Serializable
data class ProductVariantsDto(
    val id: Long? = null,
    val sku: String? = null,
    val stockQuantity: Long? = null,
    val actualPrice: Double? = null,
    val discountedPrice: Double? = null,
    val weightInGrams: Long? = null,
    val color: ColorVariantDto? = null,
    val size: SizeVariantDto? = null,

    @SerialName("primaryImageUrl") val primaryImageURL: JsonElement? = null,

    val images: JsonElement? = null,
)

@Serializable
data class ColorVariantDto(
    val id: Long? = null,
    val name: String? = null,
    val hexCode: String? = null,
)

@Serializable
data class SizeVariantDto(
    val id: Long? = null,
    val name: String? = null,
    val displayOrder: Long? = null,
    val sizeType: String? = null,
)
