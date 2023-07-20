package com.neqabty.healthcare.complains.data.source

import com.neqabty.healthcare.complains.data.api.ComplainApi
import com.neqabty.healthcare.complains.data.model.ComplainModel
import com.neqabty.healthcare.core.data.PreferencesHelper
import javax.inject.Inject

class ComplainDS @Inject constructor(private val complainApi: ComplainApi, private val preferencesHelper: PreferencesHelper) {

    suspend fun getAllComplains(): List<ComplainModel>{
        return complainApi.getAllComplains(token = "Token ${preferencesHelper.token}").data
    }
}