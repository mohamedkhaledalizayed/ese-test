package com.neqabty.healthcare.sustainablehealth.search.presentation.view.searchresult

import com.neqabty.healthcare.sustainablehealth.search.presentation.model.filters.ItemUi

interface IOnFilterListener {
    fun onFilterClicked(government: ItemUi?, profession: ItemUi?, providerType: ItemUi?, degrees: ItemUi?, area: ItemUi?)
}