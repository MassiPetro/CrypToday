package com.petrogallimassimo.cryptoday.domain

import com.petrogallimassimo.cryptoday.data.CryptoDetail
import com.petrogallimassimo.cryptoday.data.CryptoMarketChart
import com.petrogallimassimo.cryptoday.data.CryptoResponse
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun getCryptos(): Flow<List<CryptoResponse>>

    fun getDetailCrypto(id: String): Flow<CryptoDetail>

    fun getMarketChartCrypto(id: String): Flow<CryptoMarketChart>
}