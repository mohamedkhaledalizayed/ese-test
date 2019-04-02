package com.neqabty.presentation.ui.claiming

import com.neqabty.R

enum class PagerModel private constructor(val titleResId: Int, val layoutResId: Int) {
    ONE(R.string.areaDetails, R.layout.claiming1_fragment),
    TWO(R.string.serviceProviderDetails, R.layout.claiming2_fragment),
    THREE(R.string.attachPhotoDetails, R.layout.claiming3_fragment),
    FOUR(R.string.congrats, R.layout.claiming4_fragment)
}