package com.example.price_tag.data.mapper.product

import android.util.Log
import com.example.price_tag.data.mapper.category.toDomain
import com.example.price_tag.data.model.product_details.ColorVariantDto
import com.example.price_tag.data.model.product_details.ProductVariantsDto
import com.example.price_tag.data.model.product_details.SingleProductDetailsResponseDto
import com.example.price_tag.data.model.product_details.SizeVariantDto
import com.example.price_tag.domain.model.product.ColorSizeVariantGroup
import com.example.price_tag.domain.model.product.ColorVariant
import com.example.price_tag.domain.model.product.ProductVariants
import com.example.price_tag.domain.model.product.SingleProductDetails
import com.example.price_tag.domain.model.product.SizeVariant
import com.example.price_tag.domain.model.product.VariantColorSizeListMap

fun SingleProductDetailsResponseDto.toDomain(): SingleProductDetails {
//    Log.d("ProductDetailsMapper",
//          "Mapping SingleProductDetailsResponseDto to SingleProductDetails: ${data?.variants?.groupBy { it.color?.hexCode.orEmpty() }}")
    return SingleProductDetails(
        id = data?.id ?: 0L,
        name = data?.name.orEmpty(),
        slug = data?.slug.orEmpty(),
        description = data?.description.orEmpty(),
        shortDescription = data?.shortDescription.orEmpty(),
        primaryImageURL = data?.primaryImageURL.orEmpty(),
        imagesURL = data?.imageURL?.map { it.toString() } ?: emptyList(),
        basePrice = data?.basePrice ?: 0.0,
        discountedPrice = data?.discountedPrice ?: 0.0,
        brand = data?.brand.orEmpty(),
        totalRating = data?.totalRating ?: 0.0,
        totalReview = data?.totalReview ?: 0L,
        category = data?.category?.toDomain(),
        subCategory = data?.subCategory?.toDomain(),
        variants = data?.variants?.map { it.toDomain() } ?: emptyList(),
        variantsColorSizeListMap = data?.variants?.groupBy { it.color?.hexCode.orEmpty() }
                                       ?.mapValues { entry ->
                                           entry.value.map { variant ->
                                               variant.toVariantColorSizeListMap()
                                           }
                                       } ?: emptyMap(),
        colorSizeVariantGroup = data?.variants?.groupBy { it.color?.hexCode.orEmpty() }
                                    ?.map {
                                        ColorSizeVariantGroup(
                                            colorHex = it.key,
                                            variants = it.value.map { variant ->
                                                variant.toDomain()
                                            },
                                        )
                                    }
                                    ?.toList() ?: emptyList(),
    )
}

fun ProductVariantsDto.toColorVariantGroup(): ColorSizeVariantGroup {
    return ColorSizeVariantGroup(
        colorHex = color?.hexCode.orEmpty(),
        variants = listOf(this.toDomain()),
    )
}

fun ProductVariantsDto.toVariantColorSizeListMap(): VariantColorSizeListMap {
    return VariantColorSizeListMap(
        colorId = color?.id?.toInt(),
        stockQuantity = stockQuantity ?: 0L,
        actualPrice = actualPrice ?: 0.0,
        discountedPrice = discountedPrice ?: 0.0,
        weightInGrams = weightInGrams ?: 0L,
        sizeId = size?.id?.toInt(),
        sizeName = size?.name.orEmpty(),
        colorHexCode = color?.hexCode.orEmpty(),
        primaryImageURL = primaryImageURL?.toString()
            .orEmpty(),
        images = images?.let { jsonElement ->
            if (jsonElement is kotlinx.serialization.json.JsonArray) {
                jsonElement.map { it.toString() }
            } else {
                emptyList()
            }
        } ?: emptyList(),
        colorName = color?.name.orEmpty(),
        sizeType = size?.sizeType.orEmpty(),
        displayOrder = size?.displayOrder ?: 0L,
    )
}

fun ProductVariantsDto.toDomain(): ProductVariants {
    return ProductVariants(
        id = id ?: 0L,
        sku = sku.orEmpty(),
        stockQuantity = stockQuantity ?: 0L,
        actualPrice = actualPrice ?: 0.0,
        discountedPrice = discountedPrice ?: 0.0,
        weightInGrams = weightInGrams ?: 0L,
        color = color?.toDomain(),
        size = size?.toDomain(),
        primaryImageURL = primaryImageURL?.toString()
            .orEmpty(),
        images = images?.let { jsonElement ->
            if (jsonElement is kotlinx.serialization.json.JsonArray) {
                jsonElement.map { it.toString() }
            } else {
                emptyList()
            }
        } ?: emptyList(),
    )
}

fun ColorVariantDto.toDomain(): ColorVariant {
    return ColorVariant(
        id = id ?: 0L,
        name = name.orEmpty(),
        hexCode = hexCode.orEmpty(),
    )
}

fun SizeVariantDto.toDomain(): SizeVariant {
    return SizeVariant(
        id = id ?: 0L,
        name = name.orEmpty(),
        displayOrder = displayOrder ?: 0L,
        sizeType = sizeType.orEmpty(),
    )
}