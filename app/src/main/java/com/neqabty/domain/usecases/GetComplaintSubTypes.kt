package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.ComplaintTypeEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetComplaintSubTypes @Inject constructor(
        transformer: Transformer<List<ComplaintTypeEntity>>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<ComplaintTypeEntity>>(transformer) {

    companion object {
        private const val PARAM_ID = "param:ID"
    }


    fun getComplaintsSubTypes(id: String): Observable<List<ComplaintTypeEntity>> {
        val data = HashMap<String, Any>()
        data[PARAM_ID] = id
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<ComplaintTypeEntity>> {
        val id = data?.get(PARAM_ID) as String
        return neqabtyRepository.getComplaintSubTypes(id)
    }
}