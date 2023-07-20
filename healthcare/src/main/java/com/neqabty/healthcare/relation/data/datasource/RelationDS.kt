package com.neqabty.healthcare.relation.data.datasource


import com.neqabty.healthcare.relation.data.api.RelationApi
import com.neqabty.healthcare.relation.data.model.RelationModel
import javax.inject.Inject

class RelationDS @Inject constructor(private val relationApi: RelationApi) {

    suspend fun getRelations(): List<RelationModel>{
        return relationApi.getRelations().data.relations
    }

}