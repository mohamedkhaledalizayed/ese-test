package com.neqabty.data.api

import com.google.gson.annotations.SerializedName

class ResponseWrapper<T> {
    @SerializedName("status")
    var status: Status = Status()

    @SerializedName("content")
    var data: T? = null

//    abstract fun create(response: ApiResponse<Response>): ApiResponse<Response>
//
//    fun createResponse(response : T): ApiResponse<T>{
//        this.data = response
//        return this
//    }
}

class Status {
    @SerializedName("code")
    var code: Int = 0
    @SerializedName("message")
    var message: String = ""
    @SerializedName("validation")
    var validation: List<String> = mutableListOf()
}