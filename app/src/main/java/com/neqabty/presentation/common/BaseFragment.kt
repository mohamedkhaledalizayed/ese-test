package com.neqabty.presentation.common

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import androidx.navigation.NavController
import kotlinx.android.synthetic.main.main_activity.*

open class BaseFragment : Fragment() {
    fun setupToolbar(navCtrl: NavController? = null, show: Boolean = true, showUp: Boolean = true, title: String = "") {
        when (show) {
            true -> {
                (activity as AppCompatActivity).supportActionBar?.show()
                if (showUp) {
                    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    (activity as AppCompatActivity).toolbar.setNavigationOnClickListener {
                        navCtrl?.navigateUp()
                    }
                }else
                    (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(null)

            }
            false -> (activity as AppCompatActivity).supportActionBar?.hide()
        }
    }
}