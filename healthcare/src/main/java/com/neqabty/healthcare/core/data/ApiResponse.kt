package com.neqabty.healthcare.core.data

import com.google.gson.annotations.SerializedName

class ApiResponse<T> {
    @SerializedName("status")
    var status: String = ""

    @SerializedName("message")
    var message: String = ""

    @SerializedName("data")
    var data: T? = null

//    abstract fun create(response: ApiResponse<Response>): ApiResponse<Response>
//
//    fun createResponse(response : T): ApiResponse<T>{
//        this.data = response
//        return this
//    }
}