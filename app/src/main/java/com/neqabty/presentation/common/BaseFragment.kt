package com.neqabty.presentation.common

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.annotation.CallSuper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.neqabty.R

open class BaseFragment : Fragment() {
    var builder: AlertDialog.Builder? = null
    var dialog: AlertDialog? = null
    lateinit var llSuperProgressbar: LinearLayout

    fun setupToolbar(showUp: Boolean = true, show: Boolean = true) {
        when (show) {
            true -> {
                (activity as AppCompatActivity).supportActionBar?.show()
                (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(showUp)
            }
            false -> (activity as AppCompatActivity).supportActionBar?.hide()
        }
    }

    fun setToolbarTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

    fun showConnectionAlert(
            context: Context,
            retryCallback: (() -> Unit),
            cancelCallback: (() -> Unit),
            message: String? = getString(R.string.error_msg)
    ) {
        builder = AlertDialog.Builder(context)
        builder?.setTitle(getString(R.string.error))
        builder?.setMessage(message)
        builder?.setCancelable(false)
        builder?.setPositiveButton(getString(R.string.no_connection_retry)) { dialog, which ->
            try {
                retryCallback.invoke()
            } catch (e: Exception) {
            }
        }
        builder?.setNegativeButton(getString(R.string.no_connection_cancel)) { dialog, which ->
            try {
                cancelCallback.invoke()
            } catch (e: Exception) {
            }
        }

        if (dialog == null)
            dialog = builder?.create()

        if (!dialog?.isShowing!!)
            dialog?.show()
    }

    fun showAlert(
            message: String,
            title: String = getString(R.string.alert_title),
            okCallback: (() -> Unit) = { dialog?.dismiss() }
    ) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setCancelable(false)
        builder.setMessage(message)
        builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, _ ->
            try {
                okCallback.invoke()
            } catch (e: Exception) {
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    fun showTwoButtonsAlert(
            message: String,
            title: String = getString(R.string.alert_title),
            okCallback: (() -> Unit) = { dialog?.dismiss() },
            cancelCallback: (() -> Unit) = { dialog?.dismiss()},
            btnPositiveTitle: String= getString(R.string.confirm),
            btnNegativeTitle: String = getString(R.string.cancel_btn)
    ) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
        builder.setCancelable(false)
        builder.setMessage(Html.fromHtml(message))
        builder.setPositiveButton(btnPositiveTitle) { dialog, _ ->
            try {
                okCallback.invoke()
            } catch (e: Exception) {
            }
        }
        builder.setNegativeButton(btnNegativeTitle) { dialog, which ->
            try {
                cancelCallback.invoke()
                dialog?.dismiss()
            } catch (e: Exception) {
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).invalidateOptionsMenu()
        llSuperProgressbar = (activity as AppCompatActivity).findViewById(R.id.llSuperProgressbar)
        super.onViewCreated(view, savedInstanceState)
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    private fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity?.window?.decorView?.rootView?.windowToken, 0)
    }
}