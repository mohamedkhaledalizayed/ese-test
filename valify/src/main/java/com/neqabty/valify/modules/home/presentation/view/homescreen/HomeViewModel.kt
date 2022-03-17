package com.neqabty.valify.modules.home.presentation.view.homescreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.valify.modules.home.data.model.VerifyUserBody
import com.neqabty.valify.modules.home.data.model.VerifyUserResponse
import com.neqabty.valify.modules.home.domain.entity.TokenEntity
import com.neqabty.valify.modules.home.domain.interactors.GetTokenUseCase
import com.neqabty.valify.modules.home.domain.interactors.VerifyUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getTokenUseCase: GetTokenUseCase,private val verifyUserUseCase: VerifyUserUseCase) :
    ViewModel() {
    val token = MutableLiveData<String>()
    fun getToken() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getTokenUseCase.build().collect {
                    token.postValue(it.accessToken)
                }
            }catch (e:Throwable){

            }
        }
    }

    val user = MutableLiveData<VerifyUserResponse>()
    fun verifyUser(verifyUserBody: VerifyUserBody) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                verifyUserUseCase.build(verifyUserBody).collect {
                    user.postValue(it)
                }
            } catch (e: Throwable) {
                Log.e("", e.toString())
            }
        }
    }
}