package com.petrogallimassimo.cryptoday.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.gson.internal.bind.util.ISO8601Utils.format
import com.petrogallimassimo.cryptoday.data.RepositoryImpl
import com.petrogallimassimo.cryptoday.domain.model.CryptoDetailUIModel
import com.petrogallimassimo.cryptoday.domain.model.CryptoMarketChartUIModel
import kotlinx.coroutines.launch
import java.lang.String.format
import android.text.format.DateFormat
import java.text.DecimalFormat

class DetailViewModel(
    private val repository: RepositoryImpl
) : ViewModel() {

    val detailCryptoLiveData = MutableLiveData<CryptoDetailUIModel>()
    val marketChartCryptoLiveData = MutableLiveData<List<CryptoMarketChartUIModel>>()

    fun getDetailCrypto(id: String) {
        viewModelScope.launch {
            repository.getDetailCrypto(id).collect {
                detailCryptoLiveData.postValue(it.mapToCryptoDetailUIModel())
            }
        }
    }

    fun getMarketChartCrypto(id: String) {
        viewModelScope.launch {
            repository.getMarketChartCrypto(id).collect {
                marketChartCryptoLiveData.postValue(
                    it.mapToUIModel()
                )
            }
        }
    }

    fun getDate(millis: Float): String {
        return DateFormat.format("dd/MM", millis.toLong()).toString()
    }

    fun getEur(eurFloat: Float): String {
        val decimalFormat = DecimalFormat("#.###")
        return if (eurFloat < 100F) {
            decimalFormat.format(eurFloat) + "€"
        } else {
            eurFloat.toLong().toString() + "€"
        }
    }

    fun getDouble(eurFloat: Float): String {
        val decimalFormat = DecimalFormat("#.###")
        return if (eurFloat < 100F) {
            decimalFormat.format(eurFloat.toDouble())
        } else {
            eurFloat.toLong().toString()
        }
    }

    class ViewModelFactory constructor(private val repository: RepositoryImpl) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailViewModel(repository) as T
        }
    }

}