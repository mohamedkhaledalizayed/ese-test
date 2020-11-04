package com.neqabty.presentation.common

import androidx.lifecycle.MutableLiveData
import com.neqabty.MainViewState

object Constants {
    var JWT: String = ""
    var isFirebaseTokenUpdated: MutableLiveData<Boolean> = MutableLiveData(false)

}