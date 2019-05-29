package com.neqabty.presentation.ui.home

import android.arch.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetAllNews
import com.neqabty.domain.usecases.GetAllTrips
import com.neqabty.domain.usecases.GetAppVersion
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.NewsEntityUIMapper
import com.neqabty.presentation.mappers.TripsEntityUIMapper

import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val getAllNews: GetAllNews,
    private val getAllTrips: GetAllTrips,
    private val getAppVersion: GetAppVersion
) : BaseViewModel() {

    private val tripsEntityUIMapper = TripsEntityUIMapper()
    private val newsEntityUIMapper = NewsEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<HomeViewState> = MutableLiveData()

    init {
        viewState.value = HomeViewState()
    }

    fun getContent(id: String) {
        getAppVersion()
        getNews(id)
        getTrips(id)
    }


    fun getAppVersion() {
        viewState.value?.appVersion?.let {
            onContentReceived()
        } ?: addDisposable(getAppVersion.observable()
            .subscribe(
                {
                    viewState.value = viewState.value?.copy(appVersion = it.appVersion.toInt())
                    onContentReceived()
                },
                {
                    viewState.value = viewState.value?.copy(isLoading = false)
                    errorState.value = it
                }
            )
        )
    }

    fun getNews(id: String) {
        viewState.value?.news?.let {
            onContentReceived()
        } ?: addDisposable(getAllNews.getAllNews(id)
            .flatMap {
                it.let {
                    newsEntityUIMapper.observable(it)
                } ?: run {
                    throw Throwable("Something went wrong :(")
                }
            }.subscribe(
                {
                    viewState.value = viewState.value?.copy(news = it)
                    onContentReceived()
                },
                {
                    viewState.value = viewState.value?.copy(isLoading = false)
                    errorState.value = it
                }
            )
        )
    }

    fun getTrips(id: String) {
        viewState.value?.trips?.let {
            onContentReceived()
        } ?: addDisposable(getAllTrips.getAllTrips(id)
            .flatMap {
                it.let {
                    tripsEntityUIMapper.observable(it)
                }
            }.subscribe(
                {
                    viewState.value = viewState.value?.copy(trips = it)
                    onContentReceived()
                },
                {
                    viewState.value = viewState.value?.copy(isLoading = false)
                    errorState.value = it
                }
            )
        )
    }

    private fun onContentReceived() {
        if (viewState.value?.appVersion != null && viewState.value?.news != null && viewState.value?.trips != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }
}
