package com.neqabty.presentation.entities

import com.neqabty.MyApp
import com.neqabty.R

data class DegreeUI(
    var id: Int = 0,
    var profession: String? = MyApp.appResources.getString(R.string.select_all)
) {
    override fun toString(): String {
        return profession ?: ""
    }
}