package com.neqabty.presentation.ui.medicalMain

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.neqabty.presentation.ui.medicalProfessions.MedicalProfessionsFragment
import com.neqabty.presentation.ui.medicalProviders.MedicalProvidersFragment

class CategoriesTabAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        when (position) {
            1 -> {
                val medicalProfessionsFragment = MedicalProfessionsFragment()
                val bundle = Bundle()
                bundle.putString("title", "1kjb")
                bundle.putInt("categoryId", 1)
                bundle.putInt("areaID", 1)
                bundle.putInt("governID", 1)
                bundle.putString("degreeID", "1")
                bundle.putString("professionID", "1")
                medicalProfessionsFragment.arguments = bundle
                return medicalProfessionsFragment
            }
            else -> {
                val medicalProvidersFragment = MedicalProvidersFragment()
                val bundle = Bundle()
                bundle.putString("title", "1kjb")
                bundle.putInt("categoryId", 1)
                bundle.putInt("areaID", 1)
                bundle.putInt("governID", 1)
                bundle.putString("degreeID", "1")
                bundle.putString("professionID", "1")
                medicalProvidersFragment.arguments = bundle
                return medicalProvidersFragment
            }
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}