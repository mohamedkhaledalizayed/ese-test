package com.neqabty.presentation.ui.payment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.data.api.requests.ValidationRequest
import com.neqabty.databinding.PaymentFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.payment_fragment.*
import javax.inject.Inject


class PaymentFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<PaymentFragmentBinding>()

    lateinit var paymentViewModel: PaymentViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.payment_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        paymentViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(PaymentViewModel::class.java)

        initializeViews()

        paymentViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        paymentViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                paymentViewModel.getSyndicates()
            }, cancelCallback = {
                navController().navigateUp()
            })
        })

        paymentViewModel.getSyndicates()
    }

    private fun handleViewState(state: PaymentViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

    }

    fun initializeViews() {
        val params = PaymentFragmentArgs.fromBundle(arguments!!)
        startWebView("http://eea.neqabty.com/api/v1/transactions/create?amount="+params.amount+"&membership_id="+ params.memberID+"&syndicate_id="+PreferencesHelper(requireContext()).mainSyndicate+"&service_id="+ ValidationRequest().paymentType)
    }

    private fun startWebView(url: String) {
        webview.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
//                view.loadUrl(url)
                return false
            }

            //Show loader on url load
            override fun onLoadResource(view: WebView, url: String) {}

            override fun onPageFinished(view: WebView, url: String) {

                try {

//                    if (url.contains("/wallet-recharge/failed")) {
//                        finishMethod()
//                    } else if (url.contains("/wallet-recharge/pay-completed")) {
//                        webview.clearHistory()
//                        val broadcastIntent = Intent()
//                        broadcastIntent.action = "com.package.ACTION_CLASS_CABILY_MONEY_REFRESH"
//                        sendBroadcast(broadcastIntent)
//                        finishMethod()
//                    } else if (url.contains("/wallet-recharge/pay-cancel")) {
//                        finishMethod()
//                    }

                } catch (exception: Exception) {
                    exception.printStackTrace()
                }

            }
        })

        //Load url in webView
        webview.settings.javaScriptEnabled = true
        webview.loadUrl(url)
    }
//region

// endregion

    fun navController() = findNavController()
}
