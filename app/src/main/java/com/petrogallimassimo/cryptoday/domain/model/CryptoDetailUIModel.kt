package com.petrogallimassimo.cryptoday.domain.model

data class CryptoDetailUIModel(
    val description: String,
    val homepage: List<String>
) {
    override fun toString(): String {
        return "CryptoDetailUIModel(description='$description', homepage=$homepage)"
    }
}
