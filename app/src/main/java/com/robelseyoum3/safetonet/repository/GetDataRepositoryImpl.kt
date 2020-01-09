package com.robelseyoum3.safetonet.repository

import com.robelseyoum3.safetonet.model.Rockets
import com.robelseyoum3.safetonet.network.WebServices
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetDataRepositoryImpl (private val webServices: WebServices) : Repository {

    override fun getRocketRepositoriesMethod(): Single<List<Rockets>> {
        return webServices
            .fetchRockets()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}1lsjdf