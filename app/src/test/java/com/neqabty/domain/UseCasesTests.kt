package com.neqabty.domain

import com.neqabty.DomainTestUtils
import com.neqabty.domain.common.TestTransformer
import io.reactivex.Observable
import org.junit.Test
import org.mockito.Mockito

class UseCasesTests {

    @Test
    fun getWeather() { // rename test i.e. testGetWeatherUseCases_getWeather_Success ,testGetWeatherUseCases_getWeather_Error
        val weatherEntity = DomainTestUtils.getTestWeatherEntity()
        val weatherRepository = Mockito.mock(NeqabtyRepository::class.java)
        val getWeather = GetWeather(TestTransformer(), weatherRepository)

        Mockito.`when`(weatherRepository.getWeather(0.0 , 0.0)).thenReturn(Observable.just(weatherEntity))// try whenever

        getWeather.getWeather(0.0 , 0.0).test()
                .assertValue { returnedWeatherEntity ->
                    returnedWeatherEntity != null
                }
                .assertComplete()
    }
}