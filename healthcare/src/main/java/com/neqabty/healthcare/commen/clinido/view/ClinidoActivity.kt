package com.neqabty.healthcare.commen.clinido.view


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityClinidoBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ClinidoActivity : BaseActivity<ActivityClinidoBinding>() {

    private var url = ""
    override fun getViewBinding() = ActivityClinidoBinding.inflate(layoutInflater)
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        url = intent.getStringExtra("url")!!

        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, progress: Int) {

            }
        }
        binding.webView.webViewClient = HelloWebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl(url)
    }

    private class HelloWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView.canGoBack()) {
            binding.webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}