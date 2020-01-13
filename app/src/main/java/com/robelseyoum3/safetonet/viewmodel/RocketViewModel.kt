package com.robelseyoum3.safetonet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import com.robelseyoum3.safetonet.model.Rockets
import com.robelseyoum3.safetonet.repository.GetDataRepositoryImpl
import com.robelseyoum3.safetonet.repository.Repository
import io.reactivex.disposables.CompositeDisposable

class RocketViewModel (private val repository: Repository) : ViewModel() {


    private var allRocketsMutableData: MutableLiveData<List<Rockets>> = MutableLiveData()

    private var progressbarMutableData:  MutableLiveData<Boolean> = MutableLiveData()

    private val errorMutableData: MutableLiveData<Boolean> = MutableLiveData()

    private var compositeDisposable = CompositeDisposable() //we can add several observable



     fun getRockets(isActiveOnly: Boolean = false) {

        compositeDisposable.add(

            repository.getRocketRepositoriesMethod()
                .doOnSubscribe { progressbarMutableData.postValue(true) }
                .doOnError { progressbarMutableData.value = false }
                .map { it -> if(isActiveOnly) { it.filter { it.active } } else { it } }
                .subscribe(
                    {
                            rockets -> allRocketsMutableData.value =  rockets
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