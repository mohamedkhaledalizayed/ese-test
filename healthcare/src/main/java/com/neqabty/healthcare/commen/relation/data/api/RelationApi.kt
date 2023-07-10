package com.neqabty.healthcare.commen.relation.data.api


import com.neqabty.healthcare.commen.relation.data.model.RelationsTypesModel
import retrofit2.http.*

interface RelationApi {

    @GET("general-Lockups")
    suspend fun getRelations(): RelationsTypesModel

}
