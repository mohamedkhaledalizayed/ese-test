package com.neqabty.yodawy.core.utils

fun String.replaceText(): String{
    return this.replace("\\", "/")
}