package com.vedatakcan.fakestore.data.models


import com.google.gson.annotations.SerializedName
import com.vedatakcan.fakestore.domain.models.Rating


data class ProductDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("description")
    val description: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("rating")
    val rating: Rating,
)
