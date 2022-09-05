package com.neqabty.meganeqabty.home.data.source


import com.neqabty.core.data.PreferencesHelper
import com.neqabty.meganeqabty.home.data.api.HomeApi
import com.neqabty.meganeqabty.home.data.model.ComplainBody
import com.neqabty.meganeqabty.home.data.model.ads.Ad
import javax.inject.Inject

class HomeDS @Inject constructor(private val homeApi: HomeApi, private val preferencesHelper: PreferencesHelper) {

    suspend fun getAllAds(): List<Ad> {
        return homeApi.getAllAds().ads
    }

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