package com.neqabty.presentation.ui.home

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.*
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.mappers.HealthCareProjectStatusEntityUIMapper
import com.neqabty.presentation.mappers.NewsEntityUIMapper
import com.neqabty.presentation.mappers.TripsEntityUIMapper
import javax.inject.Inject

class HomeViewModel @Inject constructor(
        private val getAllNews: GetAllNews,
        private val getAllTrips: GetAllTrips,
        private val getAppVersion: GetAppVersion,
        private val getNotificationsCount: GetNotificationsCount,
        private val getHealthCareProjectStatus: GetHealthCareProjectStatus
) : BaseViewModel() {

    private val tripsEntityUIMapper = TripsEntityUIMapper()
    private val newsEntityUIMapper = NewsEntityUIMapper()
    private val healthCareProjectStatusEntityUIMapper = HealthCareProjectStatusEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<HomeViewState> = MutableLiveData()

    init {
        viewState.value = HomeViewState()
    }

    fun getContent(id: String, userNumber: String) {
        getAppVersion()
        getHealthCareProjectStatus()
//        getNews(id)
//        getTrips(id)
        if (userNumber != "")
            getNotifications(userNumber)
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
                            errorState.value = handleError(it)
                        }
                )
        )
    }


    fun getHealthCareProjectStatus() {
        viewState.value?.healthCareProjectStatusUI?.let {
            onContentReceived()
        } ?: addDisposable(getHealthCareProjectStatus.observable()
                .map {
                    it.let {
                        healthCareProjectStatusEntityUIMapper.mapFrom(it)
                    }
                }.subscribe(
                        {
                            Constants.isHealthCareProjectEnabled = (it.status == 1)
                            Constants.healthCareProjectStatusMsg = it.statusMsg
                            viewState.value = viewState.value?.copy(healthCareProjectStatusUI = it)
                            onContentReceived()
                        },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
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
                            viewState.value = viewState.value?.copy(isLoading = false)
                        },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
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
                            viewState.value = viewState.value?.copy(isLoading = false)
                        },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = handleError(it)
                        }
                )
        )
    }

    fun getNotifications(userNumber: String) {
        addDisposable(getNotificationsCount.getNotificationsCount(userNumber)
                .subscribe(
                        {
                            viewState.value = viewState.value?.copy(notificationsCount = it.notificationsCount.toInt())
                            onContentReceived()
                        },
                        {
                            viewState.value = viewState.value?.copy(notificationsCount = 0)
                            viewState.value = viewState.value?.copy(isLoading = false)
                            onContentReceived()
//                            viewState.value = viewState.value?.copy(isLoading = false)
//                            errorState.value = handleError(it)
                        }
                )
        )
    }

    private fun onContentReceived() {
        if (viewState.value?.appVersion != null && viewState.value?.healthCareProjectStatusUI != null)// && viewState.value?.news != null && viewState.value?.trips != null)
            viewState.value = viewState.value?.copy(isLoading = false)
    }
}
