package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.NotificationEntity
import com.neqabty.domain.entities.ServiceEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAllServices @Inject constructor(
    transformer: Transformer<List<ServiceEntity>>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<ServiceEntity>>(transformer) {

    companion object {
        private const val PARAM_TYPE_ID = "param:typeID"
    }

    fun getAllServices(typeID: Int): Observable<List<ServiceEntity>> {
        val data = HashMap<String, Any>()
        data[PARAM_TYPE_ID] = typeID
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<ServiceEntity>> {
        val typeID = data?.get(PARAM_TYPE_ID) as Int
        return neqabtyRepository.getAllServices(typeID)
    }
}