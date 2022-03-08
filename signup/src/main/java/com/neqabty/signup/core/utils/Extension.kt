package com.neqabty.signup.core.utils

import java.util.regex.Pattern

 fun String.isMobileValid(): Boolean {
    if(Pattern.matches("(011|012|010|015)[0-9]{8}", this)) {
        return true
    }
    return false
 }

fun String.isNationalIdValid(): Boolean {
    if(Pattern.matches("[0-9]{14}", this)) {
        return true
    }
    return false
}