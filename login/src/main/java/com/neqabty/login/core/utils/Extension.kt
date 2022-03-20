package com.neqabty.login.core.utils

import java.util.regex.Pattern

 fun String.isMobileValid(): Boolean {
    if(Pattern.matches("(011|012|010|015)[0-9]{8}", this)) {
        return true
    }
    return false
}