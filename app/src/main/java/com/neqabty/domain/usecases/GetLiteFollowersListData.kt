package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.LiteFollowersListEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetLiteFollowersListData @Inject constructor(
    transformer: Transformer<List<LiteFollowersListEntity>>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<LiteFollowersListEntity>>(transformer) {

    companion object {
        private const val PARAM_ID = "param:id"
        private const val PARAM_MOBILE_NUMBER = "param:mobileNumber"
    }

    fun getLiteFollowersListData(mobileNumber: String, id: String): Observable<List<LiteFollowersListEntity>> {
        val data = HashMap<String, String>()
        data[PARAM_ID] = id
        data[PARAM_MOBILE_NUMBER] = mobileNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<LiteFollowersListEntity>> {
        val id = data?.get(PARAM_ID) as String
        val mobileNumber = data?.get(PARAM_MOBILE_NUMBER) as String
        return neqabtyRepository.getLiteFollowersListData(mobileNumber, id)
    }
}