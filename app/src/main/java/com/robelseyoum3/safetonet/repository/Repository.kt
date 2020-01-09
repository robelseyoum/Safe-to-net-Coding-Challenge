package com.robelseyoum3.safetonet.repository

import com.robelseyoum3.safetonet.model.Rockets
import io.reactivex.Single

interface Repository {

    fun getRocketRepositoriesMethod(): Single<List<Rockets>>
}