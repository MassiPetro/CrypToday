package com.petrogallimassimo.cryptoday.domain.model

data class CryptoDetailUIModel(
    val description: String? = null,
    val homepage: List<String>? = null
) {
    override fun toString(): String {
        return "CryptoDetailUIModel(description='$description', homepage=$homepage)"
    }
}
