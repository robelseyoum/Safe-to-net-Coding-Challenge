package com.robelseyoum3.safetonet.di

import com.robelseyoum3.safetonet.repository.GetDataRepositoryImpl
import com.robelseyoum3.safetonet.viewmodel.RocketViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideViewModelFactory(getDataRepositoryImpl: GetDataRepositoryImpl): RocketViewModelFactory{
        return RocketViewModelFactory(getDataRepositoryImpl)
    }
}