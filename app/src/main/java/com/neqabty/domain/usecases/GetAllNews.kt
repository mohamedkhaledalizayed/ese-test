package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.NewsEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetAllNews @Inject constructor(
    transformer: Transformer<List<NewsEntity>>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<NewsEntity>>(transformer) {

    companion object {
        private const val PARAM_ID = "param:id"
    }

    fun getAllNews(id: String): Observable<List<NewsEntity>> {
        val data = HashMap<String, String>()
        data[PARAM_ID] = id
        return observable(data)
    }
    override fun createObservable(data: Map<String, Any>?): Observable<List<NewsEntity>> {
        val id = data?.get(GetAllNews.PARAM_ID) as String
        return neqabtyRepository.getNews(id)
    }
}