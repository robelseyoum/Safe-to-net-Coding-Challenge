package com.robelseyoum3.safetonet.di

import com.robelseyoum3.safetonet.ui.RocketsActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(RepositoryModule::class, NetworkModule::class))
interface AppComponent {

    fun inject(mainActivity: RocketsActivity)
}