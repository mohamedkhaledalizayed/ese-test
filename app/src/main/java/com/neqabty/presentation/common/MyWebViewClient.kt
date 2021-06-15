package com.neqabty.presentation.common

import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView


open class MyWebViewClient(private val mOpenFileChooserCallBack: OpenFileChooserCallBack) : WebChromeClient() {
    fun openFileChooser(uploadMsg: ValueCallback<Uri>?, acceptType: String?) {
        mOpenFileChooserCallBack.openFileChooserCallBack(uploadMsg, acceptType)
    }

    fun openFileChooser(uploadMsg: ValueCallback<Uri>?) {
        openFileChooser(uploadMsg, "")
    }

    fun openFileChooser(uploadMsg: ValueCallback<Uri>?, acceptType: String?, capture: String?) {
        openFileChooser(uploadMsg, acceptType)
    }

    override fun onShowFileChooser(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?,
                                   fileChooserParams: FileChooserParams?): Boolean {
        return mOpenFileChooserCallBack.openFileChooserCallBackAndroid5(webView, filePathCallback, fileChooserParams)
    }

    interface OpenFileChooserCallBack {
        // for API - Version below 5.0.
        fun openFileChooserCallBack(uploadMsg: ValueCallback<Uri>?, acceptType: String?)

        // for API - Version above 5.0 (contais 5.0).
        fun openFileChooserCallBackAndroid5(webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?,
                                            fileChooserParams: FileChooserParams?): Boolean
    }

}