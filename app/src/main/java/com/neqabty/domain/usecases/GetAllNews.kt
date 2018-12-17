package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.NewsEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAllNews @Inject constructor(transformer: Transformer<List<NewsEntity>>,
                                     private val neqabtyRepository: NeqabtyRepository) : UseCase<List<NewsEntity>>(transformer) {

    override fun createObservable(data: Map<String, Any>?): Observable<List<NewsEntity>> {
        return neqabtyRepository.getNews()
    }

}