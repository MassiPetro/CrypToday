package com.petrogallimassimo.cryptoday.data

import com.petrogallimassimo.cryptoday.domain.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryImpl(
    private val apiService: ApiService
) : Repository {

    override fun getCryptos() = flow {
        val cryptos = apiService.getCryptos()

        emit(cryptos)
    }

    override fun getDetailCrypto(id: String) = flow {
        val cryptoDetail = apiService.getDetailCrypto(id)

        emit(cryptoDetail)
    }

    override fun getMarketChartCrypto(id: String) = flow {
        val cryptoMarketChart = apiService.getMarketChartCrypto(id)

        emit(cryptoMarketChart)
    }

}