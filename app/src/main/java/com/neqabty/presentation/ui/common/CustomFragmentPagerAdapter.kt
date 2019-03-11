package com.neqabty.presentation.ui.common

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class CustomFragmentPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {
    val fragments = ArrayList<Fragment>()
    override fun getItem(position: Int): Fragment = fragments.get(position)

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = ""

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }
}