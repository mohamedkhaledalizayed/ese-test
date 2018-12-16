package com.neqabty.presentation.ui.common

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class CustomPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
    val fragments = ArrayList<Fragment>()
    override fun getItem(position: Int): Fragment = fragments.get(position)

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = ""

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }
}