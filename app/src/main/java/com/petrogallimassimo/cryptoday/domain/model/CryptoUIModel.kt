package com.petrogallimassimo.cryptoday.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CryptoUIModel(
    val id: String? = null,
    val name: String? = null,
    val symbol: String? = null,
    val image: String? = null,
    val currentPrice: Double? = null
) : Parcelable {
    override fun toString(): String {
        return "CryptoUiModel(id=$id, name=$name, image=$image, currentPrice=$currentPrice)"
    }
}