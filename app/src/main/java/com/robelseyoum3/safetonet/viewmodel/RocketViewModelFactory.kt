package com.robelseyoum3.safetonet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.robelseyoum3.safetonet.repository.GetDataRepositoryImpl

class RocketViewModelFactory(private val getDataRepositoryImpl: GetDataRepositoryImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RocketViewModel(getDataRepositoryImpl) as T
    }
}