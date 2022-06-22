package com.neqabty.healthcare.modules.subscribtions.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neqabty.healthcare.modules.subscribtions.domain.usecases.AddSubscriptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(private val addSubscriptionUseCase: AddSubscriptionUseCase) :
    ViewModel() {
    fun addSubscription(
        name: String,
        birthDate: String,
        email: String,
        address: String,
        job: String,
        mobile: String,
        nationalId: String,
        syndicateId: Int,
        packageId: String,
        referralNumber: String,
        personalImage: String,
        fronIdImage: String,
        backIdImage: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            addSubscriptionUseCase.build(
                name,
                birthDate,
                email,
                address,
                job,
                mobile,
                nationalId,
                syndicateId,
                packageId,
                referralNumber,
                personalImage,
                fronIdImage,
                backIdImage
            )
        }
    }
}