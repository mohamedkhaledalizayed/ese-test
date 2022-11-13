package com.neqabty.chefaa.modules.home.presentation.homescreen

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.chefaa.modules.home.domain.entities.RegistrationEntity
import com.neqabty.chefaa.modules.home.domain.usecase.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val registerUseCase: RegisterUseCase) : ViewModel() {
    val userRegistered = MutableLiveData<RegistrationEntity>()

    fun registerUser(phoneNumber:String,userId:String,countryCode:String){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                registerUseCase.build(phoneNumber,userId,countryCode).collect {
                    userRegistered.postValue(it)
                }
            }catch (e:Throwable){
                Log.e("error Register",e.toString())
                userRegistered.postValue(RegistrationEntity(false, ""))
            }
        }
    }
}