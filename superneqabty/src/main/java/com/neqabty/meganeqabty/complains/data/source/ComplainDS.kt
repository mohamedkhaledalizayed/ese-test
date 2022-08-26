package com.neqabty.meganeqabty.complains.data.source

import com.neqabty.meganeqabty.complains.data.api.ComplainApi
import javax.inject.Inject

class ComplainDS @Inject constructor(private val complainApi: ComplainApi) {

    suspend fun getAllComplains(): String{
        return complainApi.getAllComplains()
    }
}