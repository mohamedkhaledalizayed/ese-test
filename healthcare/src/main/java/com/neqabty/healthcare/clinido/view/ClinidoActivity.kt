package com.neqabty.healthcare.clinido.view


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityClinidoBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ClinidoActivity : BaseActivity<ActivityClinidoBinding>() {

    private var title = ""
    private val clinidoViewModel: ClinidoViewModel by viewModels()
    override fun getViewBinding() = ActivityClinidoBinding.inflate(layoutInflater)
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = intent.getStringExtra("title")!!

        if (title == "doctors"){
            setupToolbar("حجز أطباء")
            clinidoViewModel.getUrl(
                phone = sharedPreferences.mobile,
                type = "doctors",
                name = sharedPreferences.name,
                entityCode = sharedPreferences.code
            )
        }else{
            setupToolbar("العلاج الشهرى")
            clinidoViewModel.getUrl(
                phone = sharedPreferences.mobile,
                type = "pharmacy",
                name = sharedPreferences.name,
                entityCode = sharedPreferences.code)
        }

        clinidoViewModel.clinidoUrl.observe(this) {
            when (it.status) {
                Status.LOADING -> {
                    showProgressDialog()
                }
                Status.SUCCESS -> {
                    hideProgressDialog()
                    if (it.data!!.status) {
                        binding.webView.loadUrl(it.data.url)
                        initWebView()
                    } else {
                        Toast.makeText(this, it.data.message, Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
                Status.ERROR -> {
                    hideProgressDialog()
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    finish()
                }
            }
        }

        binding.backBtn.setOnClickListener {
            if (binding.webView.canGoBack()) {
                binding.webView.goBack()
            }else{
                finish()
            }
        }
    }

//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_BACK && binding.webView.canGoBack()) {
//            binding.webView.goBack()
//            return true
//        }
//        return super.onKeyDown(keyCode, event)
//    }

    var STORAGE_PERMISSION_CODE = 123
    val FILECHOOSER_RESULTCODE = 1
    var mUploadMessage: ValueCallback<Array<Uri>>? = null

    inner class MyWebChromeClient(var context: Context) :
        WebChromeClient() {
        override fun onShowFileChooser(
            webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>,
            fileChooserParams: FileChooserParams
        ): Boolean {
            mUploadMessage = filePathCallback
            requestStoragePermission()
            requestCameraPermission()
            return true
        }
    }

    fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                0
            )
        }
    }


    private fun requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openFileExplorer()
            return
        }
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            STORAGE_PERMISSION_CODE
        )
    }

    private fun openFileExplorer() {
        val i = Intent(Intent.ACTION_GET_CONTENT)
        i.addCategory(Intent.CATEGORY_OPENABLE)
        i.type = "image/*"
        startActivityForResult(
            Intent.createChooser(i, "File Chooser"),
            FILECHOOSER_RESULTCODE
        )
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        intent: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == FILECHOOSER_RESULTCODE) {
            if (null == mUploadMessage) return
            val result = if (intent == null || resultCode != RESULT_OK) null else intent.data
            if (result == null) {
                mUploadMessage!!.onReceiveValue(null)
            } else {
                mUploadMessage!!.onReceiveValue(arrayOf(result))
            }
            mUploadMessage = null
        }
    }

//    override fun onBackPressed() {
//        if (binding.webView.canGoBack()) {
//            binding.webView.goBack()
//            return
//        }
//        super.onBackPressed()
//    }

    private fun initWebView() {
        binding.webView.webChromeClient = MyWebChromeClient(this)
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                invalidateOptionsMenu()
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                binding.webView.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                invalidateOptionsMenu()
                binding.progressCircular.visibility = View.GONE
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                invalidateOptionsMenu()
            }
        }
        binding.webView.clearCache(true)
        binding.webView.clearHistory()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.overScrollMode = WebView.OVER_SCROLL_NEVER
        binding.webView.isHorizontalScrollBarEnabled = false
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptThirdPartyCookies(binding.webView, true)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openFileExplorer()
                //Displaying a toast
//                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
                finish()
            }
        }
    }

}
