package com.neqabty.superneqabty.home.data.source

import com.neqabty.superneqabty.home.data.api.ValifyApi
import com.neqabty.superneqabty.home.data.model.VerifyUserBody
import com.neqabty.superneqabty.home.data.model.valify.GetToken
import com.neqabty.superneqabty.home.data.model.verifyuser.VerifyUserResponse
import javax.inject.Inject

class ValifyDS @Inject constructor(private val valifyApi: ValifyApi) {
    suspend fun getToken(): GetToken {
        return valifyApi.getToken()
    }

    suspend fun verifyUser(verifyUserBody: VerifyUserBody): VerifyUserResponse {
        return valifyApi.verifyUser(verifyUserBody)
    }
}