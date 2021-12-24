package com.neqabty.presentation.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.CallSuper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.neqabty.MainActivity
import com.neqabty.R
import com.neqabty.presentation.binding.FragmentBindingAdapters
import com.neqabty.presentation.entities.AdUI
import com.neqabty.presentation.ui.ads.AdsActivity
import com.neqabty.presentation.util.PreferencesHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    @Inject
    lateinit var sharedPref: PreferencesHelper

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
        builder?.setTitle(getString(R.string.alert_title))
        builder?.setMessage(message)
        builder?.setCancelable(false)
//        builder?.setPositiveButton(getString(R.string.no_connection_retry)) { dialog, which ->
//            try {
//                retryCallback.invoke()
//            } catch (e: Exception) {
//            }
//        }
        builder?.setNegativeButton(getString(R.string.no_connection_cancel)) { dialog, which ->
            try {
                cancelCallback.invoke()
            } catch (e: Exception) {
            }
        }

        if (dialog == null)
            dialog = builder?.create()

        if (!dialog?.isShowing!! && !(requireActivity() as MainActivity).isAlertShown)
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
        cancelCallback: (() -> Unit) = { dialog?.dismiss() },
        btnPositiveTitle: String = getString(R.string.confirm),
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

    fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity?.window?.decorView?.rootView?.windowToken, 0)
    }

    fun showAds(sectionID: Int) {
        Constants.adsList.value?.let {
            openAdsActivity(sectionID)
            return
        }
        Constants.adsList.observe(this.requireActivity(), Observer {
            openAdsActivity(sectionID)
        })
    }


    private fun openAdsActivity(sectionID: Int) {
        val adsList = Constants.adsList.value?.filter { it.id == sectionID && it.type.equals("Full Screen", true) }
        if (adsList?.isNotEmpty() == true) {
            val intent = Intent(activity, AdsActivity::class.java)
            intent.putParcelableArrayListExtra("adsList", adsList as ArrayList<AdUI>)
            startActivity(intent)
        }
    }

    protected fun showBannerAd(sectionID: Int, imageView: ImageView) {
        Constants.adsList.value?.let {
            loadBannerAd(sectionID, imageView)
            return
        }
        Constants.adsList.observe(this.requireActivity(), Observer {
            loadBannerAd(sectionID, imageView)
        })
    }

    private fun loadBannerAd(sectionID: Int, imageView: ImageView) {
        val adsList = Constants.adsList.value?.filter { it.id == sectionID && it.type.contains("Banner", true) }
        if (adsList?.isNotEmpty() == true) {
            FragmentBindingAdapters(this).bindImageURL(imageView, adsList[0].imgURL)
            if (adsList[0].url.isNotBlank()) {
                imageView.setOnClickListener {
                    var url = adsList[0].url
                    if (!url.startsWith("https://") && !url.startsWith("http://")){
                        url = "http://" + url;
                    }
                    startActivity(Intent( Intent.ACTION_VIEW, Uri.parse(url)))
                }
            }
        }
    }
}