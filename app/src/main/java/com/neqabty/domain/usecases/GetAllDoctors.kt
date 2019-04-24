package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.DoctorEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAllDoctors @Inject constructor(
    transformer: Transformer<List<DoctorEntity>>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<DoctorEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<DoctorEntity>> {
        return neqabtyRepository.getAllDoctors()
    }
}