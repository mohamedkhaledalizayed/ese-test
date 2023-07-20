package com.neqabty.healthcare.relation.data.api


import com.neqabty.healthcare.relation.data.model.RelationsTypesModel
import retrofit2.http.*

interface RelationApi {

    @GET("general-Lockups")
    suspend fun getRelations(): RelationsTypesModel

}
