package com.neqabty.presentation.common

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.neqabty.R

open class BaseFragment : Fragment() {
    var builder: AlertDialog.Builder? = null
    var dialog: AlertDialog? = null
    fun setupToolbar(showUp: Boolean = true, show: Boolean = true) {
        when (show) {
            true -> {
                (activity as AppCompatActivity).supportActionBar?.show()
                (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(showUp)
            }
            false -> (activity as AppCompatActivity).supportActionBar?.hide()
        }
    }


    fun showConnectionAlert(context: Context, retryCallback: (() -> Unit), cancelCallback: (() -> Unit)) {
        builder = AlertDialog.Builder(context)
        builder?.setTitle(getString(R.string.error))
        builder?.setMessage(getString(R.string.error_msg))
        builder?.setCancelable(false)
        builder?.setPositiveButton(getString(R.string.no_connection_retry)) { dialog, which ->
            retryCallback.invoke()
        }
        builder?.setNegativeButton(getString(R.string.no_connection_cancel)) { dialog, which ->
            cancelCallback.invoke()
        }

        if(dialog == null)
            dialog = builder?.create()

        if(!dialog?.isShowing!!)
            dialog?.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).invalidateOptionsMenu()
        super.onViewCreated(view, savedInstanceState)
    }
}