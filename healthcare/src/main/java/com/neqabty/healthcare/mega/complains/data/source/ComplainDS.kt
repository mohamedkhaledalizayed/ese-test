package com.neqabty.healthcare.mega.complains.data.source

import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.mega.complains.data.api.ComplainApi
import com.neqabty.healthcare.mega.complains.data.model.ComplainModel
import javax.inject.Inject

class ComplainDS @Inject constructor(private val complainApi: ComplainApi, private val preferencesHelper: PreferencesHelper) {

    suspend fun getAllComplains(): List<ComplainModel>{
        return complainApi.getAllComplains(token = "Token ${preferencesHelper.token}").data
    }
}