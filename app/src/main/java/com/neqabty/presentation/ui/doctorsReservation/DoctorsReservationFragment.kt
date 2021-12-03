package com.neqabty.presentation.ui.doctorsReservation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.DoctorsReservationFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.common.MyWebViewClient
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DoctorsReservationFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<DoctorsReservationFragmentBinding>()

    private val doctorsReservationViewModel: DoctorsReservationViewModel by viewModels()

    @Inject
    lateinit var appExecutors: AppExecutors

    ///upload picture///////
    private var mUploadMsgForAndroid5: ValueCallback<Array<Uri>>? = null
    private var mUploadMsg: ValueCallback<Uri>? = null
    private val FILECHOOSER_RESULTCODE = 1

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.doctors_reservation_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        doctorsReservationViewModel.viewState.observe(viewLifecycleOwner, Observer {
            if (it != null) handleViewState(it)
        })
        doctorsReservationViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })

        doctorsReservationViewModel.getAuthCode(sharedPref.mobile)
    }

    private fun handleViewState(state: DoctorsReservationViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.authCode?.let {
            startWebView(Constants.VEZEETA_CONFIG.url + it)
        }
    }

//region

    private fun startWebView(url: String) {
        binding.webview.setWebViewClient(object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (url.startsWith("tel:")) {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                    startActivity(intent)
                    view.reload()
                    return true
                }
                view.loadUrl(url)
                return true
            }

            //Show loader on url load
            override fun onLoadResource(view: WebView, url: String) {}

            override fun onPageFinished(view: WebView, url: String) {
//                try {
//                } catch (exception: Exception) {
//                    exception.printStackTrace()
//                }
            }
        })

        //Load url in webView
        binding.webview.settings.domStorageEnabled = true
        binding.webview.settings.allowContentAccess = true
        binding.webview.settings.allowFileAccess = true
        binding.webview.settings.allowFileAccessFromFileURLs = true
        binding.webview.settings.allowUniversalAccessFromFileURLs = true
        binding.webview.settings.javaScriptCanOpenWindowsAutomatically = true
        binding.webview.isClickable = true
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.webChromeClient = MyWebViewClient(object : MyWebViewClient.OpenFileChooserCallBack {
            override fun openFileChooserCallBack(uploadMsg: ValueCallback<Uri>?, acceptType: String?) {
                mUploadMsg = uploadMsg
                val intent = Intent()
                intent.setType("image/*")
                intent.setAction(Intent.ACTION_GET_CONTENT)
                startActivityForResult(intent, FILECHOOSER_RESULTCODE)
            }

            override fun openFileChooserCallBackAndroid5(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: WebChromeClient.FileChooserParams?): Boolean {
                mUploadMsgForAndroid5 = filePathCallback
                val intent = Intent()
                intent.setType("image/*")
                intent.setAction(Intent.ACTION_GET_CONTENT)
                startActivityForResult(intent, FILECHOOSER_RESULTCODE)
                return true;
            }
        })
        binding.webview.loadUrl(url)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            if (mUploadMsg != null) {
                mUploadMsg!!.onReceiveValue(null)
            }
            if (mUploadMsgForAndroid5 != null) {         // for android 5.0+
                mUploadMsgForAndroid5!!.onReceiveValue(null)
            }
            return
        }
        when (requestCode) {
            FILECHOOSER_RESULTCODE -> {
                try {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        if (mUploadMsg == null) {
                            return
                        }
                        mUploadMsg!!.onReceiveValue(data?.data)
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (mUploadMsgForAndroid5 == null) {        // for android 5.0+
                            return
                        }
                        mUploadMsgForAndroid5!!.onReceiveValue(arrayOf(data?.data as Uri))
                        mUploadMsgForAndroid5 = null
                        return
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    // endregion
    fun navController() = findNavController()
}
