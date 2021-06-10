package com.neqabty.presentation.ui.onlinePharmacy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.data.api.requests.ValidationRequest
import com.neqabty.databinding.OnlinePharmacyFragmentBinding
import com.neqabty.databinding.PaymentFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import javax.inject.Inject

class OnlinePharmacyFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<OnlinePharmacyFragmentBinding>()

    lateinit var onlinePharmacyViewModel: OnlinePharmacyViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.online_pharmacy_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onlinePharmacyViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(OnlinePharmacyViewModel::class.java)

        onlinePharmacyViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        onlinePharmacyViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })

        onlinePharmacyViewModel.getURL(PreferencesHelper(requireContext()).user)
    }

    private fun handleViewState(state: OnlinePharmacyViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.url?.let {
            startWebView(it)
        }
    }

//region

    private fun startWebView(url: String) {
        binding.webview.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
//                view.loadUrl(url)
                return false
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
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.loadUrl(url)
    }

    // endregion
    fun navController() = findNavController()
}
