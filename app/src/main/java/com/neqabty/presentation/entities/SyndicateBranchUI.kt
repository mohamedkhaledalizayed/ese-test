package com.neqabty.presentation.entities

data class SyndicateBranchUI(
    var id: Int = 0,
    var name: String?,
    var location: String?,
    var address: String?,
    var phone: String?
) {
    override fun toString(): String {
        return name ?: ""
    }
}