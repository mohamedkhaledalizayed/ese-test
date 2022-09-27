package com.neqabty.presentation.common

data class SpinnerModel ( val id: Int = -1, val name: String, var isSelected: Boolean = false){
    override fun toString(): String {
        return name
    }
}