package com.neqabty.login.modules.home.presentation.view.homescreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.login.modules.home.domain.entity.SyndicateEntity
import com.neqabty.login.modules.home.domain.interactors.GetSyndicateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SyndicatesViewModel @Inject constructor(private val getSyndicateUseCase: GetSyndicateUseCase) :
    ViewModel() {
    val syndicates = MutableLiveData<List<SyndicateEntity>>()
    fun getSyndicates() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getSyndicateUseCase.build().collect {
                    syndicates.postValue(it)
                }
            }catch (e:Throwable){
                Log.e("",e.toString())
            }
        }
    }
}