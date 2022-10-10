package com.neqabty.core.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.neqabty.core.data.PreferencesHelper
import com.neqabty.core.R
import com.neqabty.core.utils.LocaleHelper
import javax.inject.Inject

//
abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: PreferencesHelper
    lateinit var binding: B
    abstract fun getViewBinding(): B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(getAppTheme())
        binding = getViewBinding()
        setSupportActionBar(binding.root.findViewById(R.id.toolbar))
        binding.root.findViewById<Toolbar>(R.id.toolbar)
            ?.setNavigationOnClickListener { onBackPressed() }
        window.setBackgroundDrawableResource(R.color.white)
        binding.root.fitsSystemWindows = true
    }

    override fun onResume() {
        super.onResume()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        invalidateOptionsMenu()
    }

    protected fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window?.decorView?.rootView?.windowToken, 0)
    }

    fun getAppTheme() :Int{
        return when(sharedPreferences.fontSize) {
            "small" -> R.style.AppTheme_NoActionBar_SmallText
            "large" -> R.style.AppTheme_NoActionBar_LargeText
            else -> R.style.AppTheme_NoActionBar
        }
    }
    //region Alerts//
    //endregion

    //region toolbar//
    @SuppressLint("ResourceAsColor")
    fun setupToolbar(titleResId: Int, showUp: Boolean = true, show: Boolean = true) {
        setupToolbar(getString(titleResId), showUp, show)
    }

    @SuppressLint("ResourceAsColor")
    fun setupToolbar(title: String, showUp: Boolean = true, show: Boolean = true) {
        when (show) {
            true -> {
                supportActionBar?.show()
                supportActionBar?.title = title
                supportActionBar?.setDisplayHomeAsUpEnabled(showUp)
            }
            false -> supportActionBar?.hide()
        }
    }
    //endregion//

    //region Alerts//

    fun showAlert(
        message: String,
        title: String = getString(R.string.alert_title),
        okCallback: (() -> Unit)
    ) {
        val builder = AlertDialog.Builder(this)
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
    //endregion

    private fun verifyAvailableNetwork():Boolean{
        val connectivityManager=getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo=connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }

    override fun onStart() {
        super.onStart()
        if (!verifyAvailableNetwork()){
            showAlert(getString(R.string.internet_message), getString(R.string.internet_title)) { finish() }
        }
        LocaleHelper().setLocale(this,"ar")
    }
}