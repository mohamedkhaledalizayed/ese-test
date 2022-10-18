package com.neqabty.chefaa.core.utils

fun String.replaceText(): String{
    return this.replace("\\", "/")
}