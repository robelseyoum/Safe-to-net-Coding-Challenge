package com.robelseyoum3.safetonet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import com.robelseyoum3.safetonet.model.Rockets
import com.robelseyoum3.safetonet.repository.GetDataRepositoryImpl
import io.reactivex.disposables.CompositeDisposable

class RocketViewModel (private val getDataRepositoryImpl: GetDataRepositoryImpl) : ViewModel() {


    private var allRocketsMutableData: MutableLiveData<List<Rockets>> = MutableLiveData()

    private var progressbarMutableData:  MutableLiveData<Boolean> = MutableLiveData()

    private val errorMutableData: MutableLiveData<Boolean> = MutableLiveData()

    private var compositeDisposable = CompositeDisposable() //we can add several observable


    fun getAllRocketsData() {

        compositeDisposable.add(
            getDataRepositoryImpl.getRocketRepositoriesMethod()
                .doOnSubscribe { progressbarMutableData.postValue(true) }
                .doOnError { progressbarMutableData.value = false }
                .map { it -> it.filter { it.active } }
                .subscribe(
                    {
                        rockets -> allRocketsMutableData.value = rockets
                        progressbarMutableData.value = false
                    },
                    {
                        errorMutableData.value = true
                    }
                )
        )
    }

    fun returnAllRocketsResult() = allRocketsMutableData

    fun returnError() = errorMutableData

    fun returnProgressBarValue() = progressbarMutableData


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }


}