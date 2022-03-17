package com.neqabty.valify.modules.home.data.source


import com.neqabty.valify.modules.home.domain.entity.TokenEntity
import com.neqabty.valify.modules.home.data.api.ValifyApi
import com.neqabty.valify.modules.home.data.model.GetToken
import com.neqabty.valify.modules.home.data.model.VerifyUserBody
import com.neqabty.valify.modules.home.data.model.VerifyUserResponse
import javax.inject.Inject

class ValifyDS @Inject constructor(private val valifyApi: ValifyApi) {
    suspend fun getToken(): GetToken {
        return valifyApi.getToken()
    }

    suspend fun verifyUser(verifyUserBody: VerifyUserBody): VerifyUserResponse {
        return valifyApi.verifyUser(verifyUserBody)
    }
}