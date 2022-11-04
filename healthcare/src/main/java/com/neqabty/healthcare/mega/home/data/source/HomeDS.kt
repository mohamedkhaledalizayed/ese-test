package com.neqabty.healthcare.mega.home.data.source


import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.mega.home.data.api.HomeApi
import com.neqabty.healthcare.mega.home.data.model.ComplainBody
import javax.inject.Inject

class HomeDS @Inject constructor(private val homeApi: HomeApi, private val preferencesHelper: PreferencesHelper) {

    suspend fun addComplain(mobile: String, email: String, message: String): String {
        if (preferencesHelper.isAuthenticated){
            return homeApi.addComplainAuth(
                token = "Token ${preferencesHelper.token}",
                ComplainBody(
                    message = message,
                    entity = preferencesHelper.mainSyndicate,
                    mobile = mobile,
                    email =  email)
            ).receiptId
        }else{
            return homeApi.addComplain(
                ComplainBody(
                    message = message,
                    entity = preferencesHelper.mainSyndicate,
                    mobile = mobile,
                    email =  email)
            ).receiptId
        }
    }

}