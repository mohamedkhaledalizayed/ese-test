package com.neqabty.presentation.ui.signup

import com.neqabty.R

enum class Model private constructor(val titleResId: Int, val layoutResId: Int) {
    ONE(R.string.personalDetails, R.layout.signup1_fragment),
    TWO(R.string.confirm, R.layout.signup2_fragment),
    THREE(R.string.thanks, R.layout.signup3_fragment)
}