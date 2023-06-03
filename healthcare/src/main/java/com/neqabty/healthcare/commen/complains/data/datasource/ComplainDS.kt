package com.neqabty.healthcare.commen.complains.data.datasource

import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.commen.complains.data.api.ComplainApi
import com.neqabty.healthcare.commen.complains.data.model.getcomplains.ComplainModel
import com.neqabty.healthcare.commen.complains.data.model.ComplainBody
import javax.inject.Inject

class ComplainDS @Inject constructor(private val complainApi: ComplainApi, private val preferencesHelper: PreferencesHelper) {

    suspend fun getAllComplains(): List<ComplainModel>{
        return complainApi.getAllComplains(token = "Token ${preferencesHelper.token}").data
    }

    suspend fun addComplain(mobile: String, email: String, message: String): String {
        if (preferencesHelper.isAuthenticated){
            return complainApi.addComplainAuth(
                token = "Token ${preferencesHelper.token}",
                ComplainBody(
                    message = message,
                    entity = preferencesHelper.mainSyndicate,
                    mobile = mobile,
                    email =  email)
            ).receiptId
        }else{
            return complainApi.addComplain(
                ComplainBody(
                    message = message,
                    entity = preferencesHelper.mainSyndicate,
                    mobile = mobile,
                    email =  email)
            ).receiptId
        }
    }

}