package com.neqabty.data.api

import com.google.gson.annotations.SerializedName

abstract class ApiResponse<T> {
    @SerializedName("status")
    var status: String = ""

    @SerializedName("status_message_en")
    var enMsg: String = ""

    @SerializedName("status_message_ar")
    var arMsg: String = ""

    @SerializedName("data")
    var data: T? = null

//    abstract fun create(response: ApiResponse<Response>): ApiResponse<Response>
//
//    fun createResponse(response : T): ApiResponse<T>{
//        this.data = response
//        return this
//    }
}