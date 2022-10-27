package com.petrogallimassimo.cryptoday.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.petrogallimassimo.cryptoday.data.RepositoryImpl
import com.petrogallimassimo.cryptoday.domain.model.CryptoUIModel
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: RepositoryImpl
) : ViewModel() {

    val cryptosListLiveData = MutableLiveData<List<CryptoUIModel>>()

    fun getCryptos() {
        viewModelScope.launch {
            repository.getCryptos().collect {
                cryptosListLiveData.postValue(
                    it.map { response ->
                        response.mapToCryptoUiModel()
                    }
                )
            }
        }
    }

    class ViewModelFactory constructor(private val repository: RepositoryImpl) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(repository) as T
        }
    }
}