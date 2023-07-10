package com.neqabty.healthcare.commen.relation.data.datasource


import com.neqabty.healthcare.commen.relation.data.api.RelationApi
import com.neqabty.healthcare.commen.relation.data.model.RelationModel
import javax.inject.Inject

class RelationDS @Inject constructor(private val relationApi: RelationApi) {

    suspend fun getRelations(): List<RelationModel>{
        return relationApi.getRelations().data.relations
    }

}