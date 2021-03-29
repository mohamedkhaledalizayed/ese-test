package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import io.reactivex.Observable
import javax.inject.Inject

class CreateComplaint @Inject constructor(
    transformer: Transformer<Unit>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<Unit>(transformer) {

    companion object {
        private const val PARAM_NAME = "param:name"
        private const val PARAM_MOBILE = "param:phone"
        private const val PARAM_CAT_ID = "param:catId"
        private const val PARAM_SUBCAT_ID = "param:subCatId"
        private const val PARAM_DETAILS = "param:details"
        private const val PARAM_TOKEN = "param:token"
        private const val PARAM_MEMBER_NUMBER = "param:number"
    }

    fun createComplaint(name: String, phone: String, catId: String, subCatId: String, body: String, token: String, memberNumber: String): Observable<Unit> {
        val data = HashMap<String, String>()
        data[PARAM_NAME] = name
        data[PARAM_MOBILE] = phone
        data[PARAM_CAT_ID] = catId
        data[PARAM_SUBCAT_ID] = subCatId
        data[PARAM_DETAILS] = body
        data[PARAM_TOKEN] = token
        data[PARAM_MEMBER_NUMBER] = memberNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Unit> {
        val name = data?.get(PARAM_NAME) as String
        val phone = data?.get(PARAM_MOBILE) as String
        val catId = data?.get(PARAM_CAT_ID) as String
        val subCatId = data?.get(PARAM_SUBCAT_ID) as String
        val details = data?.get(PARAM_DETAILS) as String
        val token = data?.get(PARAM_TOKEN) as String
        val memberNumber = data?.get(PARAM_MEMBER_NUMBER) as String
        return neqabtyRepository.createComplaint(name, phone, catId, subCatId, details, token, memberNumber)
    }
}