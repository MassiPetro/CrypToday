package com.petrogallimassimo.cryptoday.data

import com.petrogallimassimo.cryptoday.domain.model.CryptoMarketChartUIModel
import java.util.*

/*
data class CryptoMarketChart(
    val prices: List<Price>
) {
    data class Price(
        val priceValue: List<Double>
    ) {
        fun mapToUIModel() = CryptoMarketChartUIModel(
            cryptoValue = priceValue[0],
            eurValue = priceValue[1]
        )
    }
}
*/

data class CryptoMarketChart(
    val prices: List<List<Double>>
) {
    fun mapToUIModel() = prices.map {
        CryptoMarketChartUIModel(
            dateMillis = it[0],
            eurValue = it[1]
        )
    }
}