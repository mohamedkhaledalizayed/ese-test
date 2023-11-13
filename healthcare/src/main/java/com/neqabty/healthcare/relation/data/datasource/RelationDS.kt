package com.neqabty.healthcare.relation.data.datasource


import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.relation.data.api.RelationApi
import com.neqabty.healthcare.relation.data.model.RelationModel
import javax.inject.Inject

class RelationDS @Inject constructor(private val relationApi: RelationApi, private val preferencesHelper: PreferencesHelper) {

    suspend fun getRelations(): List<RelationModel>{
        return relationApi.getRelations(token = preferencesHelper.token).data.relations
    }

}