package com.neqabty.data.mappers

import com.neqabty.data.entities.NewsData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.NewsEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsDataEntityMapper @Inject constructor() : Mapper<NewsData, NewsEntity>() {

    override fun mapFrom(from: NewsData): NewsEntity {
        return NewsEntity(
                id = from.id,
                updatedAt = from.updatedAt,
                createdAt = from.createdAt,
                createdBy = from.createdBy,
                updatedBy = from.updatedBy,
                title = from.title,
                img = from.img,
                desc = from.desc,
                date = from.date,
                time = from.time,
                source = from.source,
                mainSyndicateId = from.mainSyndicateId,
                subSyndicateId = from.subSyndicateId
        )
    }
}
