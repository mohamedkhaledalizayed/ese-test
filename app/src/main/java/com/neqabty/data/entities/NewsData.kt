package com.neqabty.data.entities
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response


data class NewsData(
    @field:SerializedName("news_id")
    var id: Int = 0,
    @field:SerializedName("news_title")
    var title: String?,
    @field:SerializedName("news_img")
    var img: String?,
    @field:SerializedName("news_desc")
    var desc: String?,
    @field:SerializedName("date")
    var date: String?,
    @field:SerializedName("time")
    var time: String?,
    @field:SerializedName("source")
    var source: String?,
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
) : Response()