package com.neqabty.presentation.entities

data class ArchiveUploadCategoryUI(
    var id: Int = 0,
    var name: String?
){
    override fun toString(): String {
        return name ?: ""
    }
}