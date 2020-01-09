package com.robelseyoum3.safetonet.network

import com.robelseyoum3.safetonet.model.Rockets
import io.reactivex.Single
import retrofit2.http.GET

interface WebServices {

    @GET
    fun fetchRockets(): Single<List<Rockets>>

}