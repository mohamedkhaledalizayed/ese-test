package com.neqabty.healthcare.modules.search.presentation.view.searchresult

interface IOnFilterListener {
    fun onFilterClicked(government: String, city: String)
}