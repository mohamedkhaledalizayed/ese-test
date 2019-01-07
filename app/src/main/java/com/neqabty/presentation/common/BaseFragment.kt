package com.neqabty.presentation.common

import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

open class BaseFragment : Fragment() {
    fun setupToolbar(showUp: Boolean = true, show: Boolean = true) {
        when (show) {
            true -> {
                (activity as AppCompatActivity).supportActionBar?.show()
                (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(showUp)
            }
            false -> (activity as AppCompatActivity).supportActionBar?.hide()
        }
    }
}