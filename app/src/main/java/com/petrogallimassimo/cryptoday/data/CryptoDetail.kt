package com.petrogallimassimo.cryptoday.data

import com.google.gson.annotations.SerializedName
import com.petrogallimassimo.cryptoday.domain.model.CryptoDetailUIModel

data class CryptoDetail(
    val description: CryptoDescription? = null,
    val links: CryptoLinks? = null
) {
    data class CryptoDescription(
        @SerializedName("en") val englishDescription: String
    )

    data class CryptoLinks(
        val homepage: List<String>
    )

    fun mapToCryptoDetailUIModel() = CryptoDetailUIModel(
        description = description?.englishDescription,
        homepage = links?.homepage
    )
}