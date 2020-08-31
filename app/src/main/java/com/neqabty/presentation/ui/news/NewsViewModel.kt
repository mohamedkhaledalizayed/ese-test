package com.neqabty.presentation.ui.news

import androidx.lifecycle.MutableLiveData
import com.neqabty.domain.usecases.GetAllNews
import com.neqabty.presentation.common.BaseViewModel
import com.neqabty.presentation.common.SingleLiveEvent
import com.neqabty.presentation.entities.NewsUI
import com.neqabty.presentation.mappers.NewsEntityUIMapper

import javax.inject.Inject

class NewsViewModel @Inject constructor(private val getAllNews: GetAllNews) : BaseViewModel() {

    private val newsEntityUIMapper = NewsEntityUIMapper()
    var errorState: SingleLiveEvent<Throwable> = SingleLiveEvent()
    var viewState: MutableLiveData<NewsViewState> = MutableLiveData()

    init {
        viewState.value = NewsViewState()
    }

    fun getNews(id: String) {
        viewState.value?.news?.let {
            onNewsReceived(it)
        } ?: addDisposable(getAllNews.getAllNews(id)
                .flatMap {
                    it.let {
                        newsEntityUIMapper.observable(it)
                    }
                }.subscribe(
                        { onNewsReceived(it) },
                        {
                            viewState.value = viewState.value?.copy(isLoading = false)
                            errorState.value = it
                        }
                )
        )
    }

    private fun onNewsReceived(news: List<NewsUI>) {
        val newViewState = viewState.value?.copy(
                isLoading = false,
                news = news)
        viewState.value = newViewState
    }
}
