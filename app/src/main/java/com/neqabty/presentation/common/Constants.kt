package com.neqabty.presentation.common

import androidx.lifecycle.MutableLiveData
import com.neqabty.MainViewState

object Constants {
    var JWT: String = ""
    var isFirebaseTokenUpdated: MutableLiveData<Boolean> = MutableLiveData(false)

    var CLAIMING = 1
    var TRIPS = 2
    var RECORDS = 3
    var UPDATE_DATA = 4
    var COMPLAINTS = 5
    var CORONA = 6
    var MEDICAL_RENEW = 7
    var MEDICAL_RENEW_UPDATE = 8


    var DELIVERY_LOCATION_SYNDICATE = 1
    var DELIVERY_LOCATION_HOME = 2

    var PAYMENT_TYPE_TRIPS = "9991"
    var PAYMENT_TYPE_RECORDS = "9992"
    var PAYMENT_TYPE_MEDICAL_RENEW = "9993"
}