package com.petrogallimassimo.cryptoday.data

import com.google.gson.annotations.SerializedName
import com.petrogallimassimo.cryptoday.domain.model.CryptoUIModel

data class CryptoResponse(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("symbol") val symbol: String? = null,
    @SerializedName("image") val image: String? = null,
    @SerializedName("current_price") val currentPrice: Double? = null
) {
    fun mapToCryptoUiModel() = CryptoUIModel(
        id = id ?: "",
        name = name ?: "",
        symbol = symbol ?: "",
        image = image ?: "",
        currentPrice = currentPrice ?: 0.0
    )

}




