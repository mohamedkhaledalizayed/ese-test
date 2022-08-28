package com.neqabty.meganeqabty.complains.data.source

import com.neqabty.core.data.PreferencesHelper
import com.neqabty.meganeqabty.complains.data.api.ComplainApi
import com.neqabty.meganeqabty.complains.data.model.ComplainModel
import javax.inject.Inject

class ComplainDS @Inject constructor(private val complainApi: ComplainApi, private val preferencesHelper: PreferencesHelper) {

    suspend fun getAllComplains(): List<ComplainModel>{
        return complainApi.getAllComplains(token = "Token ${preferencesHelper.token}").data
    }
}