package com.neqabty.healthcare.modules.search.data.model

data class Response<T>(val status: Boolean = true, val message: String = "", val data: T)
