package com.neqabty.data.entities

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

@Entity(primaryKeys = ["id"])
data class NewsData(
        @field:SerializedName("news_id")
        var id: Int = 0,
        @field:SerializedName("news_title")
        var title: String?,
        @field:SerializedName("news_img")
        var img: String?,
        @field:SerializedName("news_desc")
        var desc: String?,
        @field:SerializedName("main_syndicate_id")
        var mainSyndicateId: String?,
        @field:SerializedName("sub_syndicate_id")
        var subSyndicateId: String?,
        @field:SerializedName("created_by")
        var createdBy: String?,
        @field:SerializedName("updated_by")
        var updatedBy: String?,
        @field:SerializedName("created_at")
        var createdAt: String?,
        @field:SerializedName("updated_at")
        var updatedAt: String?
): Response()