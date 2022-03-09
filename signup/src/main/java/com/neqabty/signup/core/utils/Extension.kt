package com.neqabty.signup.core.utils

import java.util.regex.Pattern

 fun String.isMobileValid(): Boolean {
    if(Pattern.matches("(011|012|010|015)[0-9]{8}", this)) {
        return true
    }
    return false
 }

fun String.isNationalIdValid(): Boolean {
    if(Pattern.matches("(2|3)[0-9][0-9][0-1][0-9][0-3][0-9](01|02|03|04|05|11|12|13|14|15|16|17|18|19|21|22|23|24|25|26|27|28|29|31|32|33|34|35|88)\\d\\d\\d\\d\\d", this)) {
        return true
    }
    return false
}