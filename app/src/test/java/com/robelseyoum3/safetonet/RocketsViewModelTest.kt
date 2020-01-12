package com.robelseyoum3.safetonet

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.robelseyoum3.safetonet.model.Engines
import com.robelseyoum3.safetonet.model.Rockets
import com.robelseyoum3.safetonet.repository.Repository
import com.robelseyoum3.safetonet.viewmodel.RocketViewModel
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class RocketsViewModelTest {

    @Rule
    @JvmField
    var testRule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: Repository

    lateinit var rocketViewModel: RocketViewModel

    @Mock
    lateinit var engines: Engines

    private val allRocketMutableData: androidx.lifecycle.Observer<List<Rockets>> = mock()
    private val errorMutableData: androidx.lifecycle.Observer<Boolean> = mock()
    private val progressBarMutableData: androidx.lifecycle.Observer<Boolean> = mock()



    @Before
    fun setup(){
        rocketViewModel = RocketViewModel(repository)

        rocketViewModel.returnAllRocketsResult().observeForever(allRocketMutableData)
        rocketViewModel.returnProgressBarValue().observeForever(progressBarMutableData)
        rocketViewModel.returnError().observeForever(errorMutableData)
    }

    private val rocket_name = "Falcon Heavy"
    private val engines_count = 9
    private val country = "United States"
    private val active = true


    @Test
    fun getRockets_with_success(){
        `when`(engines.number).thenReturn(engines_count)

        val rockets = mutableListOf(Rockets(active, country, engines, rocket_name))

        `when`(repository.getRocketRepositoriesMethod()).thenReturn(Single.just(rockets))

        rocketViewModel.getAllRocketsData()

        verify(allRocketMutableData, times(1)).onChanged(rockets)
        verify(progressBarMutableData, times(1)).onChanged(true)
        verify(errorMutableData, times(0)).onChanged(false)

    }

    @Test
    fun getRockets_ReturnError() {
        `when`(engines.number).thenReturn(engines_count)

        val error = "Error Message"

        `when`(repository.getRocketRepositoriesMethod()).thenReturn(Single.error(RuntimeException(error)))

        rocketViewModel.getAllRocketsData()

        verify(allRocketMutableData, times(0)).onChanged(ArgumentMatchers.anyList())

        verify(progressBarMutableData, times(1)).onChanged(false)

        verify(errorMutableData, times(1)).onChanged(true)
    }


}