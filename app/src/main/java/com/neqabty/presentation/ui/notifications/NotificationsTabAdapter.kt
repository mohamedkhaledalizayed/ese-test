package com.neqabty.presentation.ui.notifications

import android.content.Context
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.NotificationItemBinding
import com.neqabty.presentation.entities.NotificationUI
import com.neqabty.presentation.ui.home.HomeFragment
import com.neqabty.presentation.ui.news.NewsFragment
import com.neqabty.presentation.ui.subsyndicates.SubSyndicatesFragment
import com.neqabty.ui.presentation.common.DataBoundListAdapter

class NotificationsTabAdapter (private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment? {
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
            else -> return null
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}