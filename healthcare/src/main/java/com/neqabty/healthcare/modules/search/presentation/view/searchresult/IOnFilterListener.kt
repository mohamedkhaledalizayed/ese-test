package com.neqabty.healthcare.modules.search.presentation.view.searchresult

import com.neqabty.healthcare.modules.search.presentation.model.filters.ItemUi

interface IOnFilterListener {
    fun onFilterClicked(government: ItemUi?, profession: ItemUi?, providerType: ItemUi?)
}