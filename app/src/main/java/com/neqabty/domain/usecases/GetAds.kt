package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.AdEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAds @Inject constructor(
    transformer: Transformer<List<AdEntity>>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<AdEntity>>(transformer) {

    companion object {
        private const val PARAM_SECTION_ID = "param:sectionID"
    }

    fun getAds(
        sectionID: Int
    ): Observable<List<AdEntity>> {
        val data = HashMap<String, Any>()
        data[PARAM_SECTION_ID] = sectionID
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<AdEntity>> {
        val sectionID = data?.get(PARAM_SECTION_ID) as Int
        return neqabtyRepository.getAds(sectionID)
    }
}