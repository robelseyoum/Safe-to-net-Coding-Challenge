package com.robelseyoum3.safetonet.network

import com.robelseyoum3.safetonet.model.Rockets
import com.robelseyoum3.safetonet.util.Constant
import io.reactivex.Single
import retrofit2.http.GET

interface WebServices {

    @GET(Constant.ENDPOINT_URL)
    fun fetchRockets(): Single<List<Rockets>>

}