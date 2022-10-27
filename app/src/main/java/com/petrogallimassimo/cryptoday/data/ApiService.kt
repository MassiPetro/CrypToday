package com.petrogallimassimo.cryptoday.data

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("coins/markets?vs_currency=eur&order=market_cap_desc&per_page=10&page=1&sparkline=false")
    suspend fun getCryptos(): List<CryptoResponse>

    @GET("coins/{id}?localization=false&tickers=false&market_data=false&community_data=false&developer_data=false&sparkline=false")
    suspend fun getDetailCrypto(@Path("id") id: String): CryptoDetail

    @GET("coins/{id}/market_chart?vs_currency=eur&days=7&interval=daily")
    suspend fun getMarketChartCrypto(@Path("id") id: String): CryptoMarketChart

}