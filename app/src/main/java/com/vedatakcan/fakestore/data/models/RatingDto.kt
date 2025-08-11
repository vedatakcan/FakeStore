package com.vedatakcan.fakestore.data.models

import com.google.gson.annotations.SerializedName

data class RatingDto(
    @SerializedName("rate")
    val rate: Double,
    @SerializedName("count")
    val count: Int
)
