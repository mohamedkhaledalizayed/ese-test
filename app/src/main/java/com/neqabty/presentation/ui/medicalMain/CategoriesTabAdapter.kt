package com.neqabty.presentation.ui.medicalMain

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.neqabty.presentation.ui.medicalProviders.MedicalProvidersFragment

class CategoriesTabAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int, var categoriesIDList: List<Int>, var categoriesNameList: List<String>, var areaID: Int, var governID: Int) : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        bundle.putString("title", categoriesNameList[position])
        bundle.putInt("categoryId", categoriesIDList[position])
        bundle.putInt("areaID", areaID)
        bundle.putInt("governID", governID)
        bundle.putString("degreeID", "")
        bundle.putString("professionID", "")
//        when (position) {
//            6 -> {
//                val medicalProfessionsFragment = MedicalProfessionsFragment()
//                medicalProfessionsFragment.arguments = bundle
//                return medicalProfessionsFragment
//            }
//            else -> {
        val medicalProvidersFragment = MedicalProvidersFragment()
        medicalProvidersFragment.arguments = bundle
        return medicalProvidersFragment
//            }
//        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}