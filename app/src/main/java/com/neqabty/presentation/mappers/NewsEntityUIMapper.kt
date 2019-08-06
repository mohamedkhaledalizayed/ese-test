package com.neqabty.presentation.mappers

import android.text.format.DateFormat
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.NewsEntity
import com.neqabty.presentation.entities.NewsUI
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsEntityUIMapper @Inject constructor() : Mapper<NewsEntity, NewsUI>() {

    override fun mapFrom(from: NewsEntity): NewsUI {
        return NewsUI(
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