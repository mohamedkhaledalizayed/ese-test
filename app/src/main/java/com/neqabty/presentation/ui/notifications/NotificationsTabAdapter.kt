package com.neqabty.presentation.ui.notifications

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class NotificationsTabAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                val notificationsListFragment = NotificationsListFragment()
                val bundle = Bundle()
                bundle.putInt("typeID", 1)
                notificationsListFragment.arguments = bundle
                return notificationsListFragment
            }
            1 -> {
                val notificationsListFragment = NotificationsListFragment()
                val bundle = Bundle()
                bundle.putInt("typeID", 2)
                notificationsListFragment.arguments = bundle
                return notificationsListFragment
            }
            else -> return NotificationsListFragment()
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}