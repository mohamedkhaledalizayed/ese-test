package com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.view.searchresult

import com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.model.filters.ItemUi

interface IOnFilterListener {
    fun onFilterClicked(government: ItemUi?, profession: ItemUi?, providerType: ItemUi?, degrees: ItemUi?, area: ItemUi?)
}