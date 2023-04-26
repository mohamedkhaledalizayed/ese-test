package com.neqabty.healthcare.core.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.onboarding.intro.view.IntroActivity
import com.neqabty.healthcare.commen.onboarding.signup.view.SignupActivity
import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.core.home_general.GeneralHomeActivity
import com.neqabty.healthcare.core.home_syndicates.view.SyndicatesHomeActivity
import com.neqabty.healthcare.core.utils.LocaleHelper
import com.neqabty.healthcare.mega.home.view.MegaHomeActivity
import com.neqabty.healthcare.news.view.newslist.NewsListActivity
import com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.SehaHomeActivity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.view.selectnetwork.SelectNetworkActivity
import javax.inject.Inject


abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: PreferencesHelper
    lateinit var binding: B
    abstract fun getViewBinding(): B
    var progressDialog: Dialog? = null
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAnimation()
//        setTheme(getAppTheme())
        binding = getViewBinding()
//        setSupportActionBar(binding.root.findViewById(R.id.layout_toolbar))
//        binding.root.findViewById<Toolbar>(R.id.layout_toolbar)
//            ?.setNavigationOnClickListener { onBackPressed() }
//
//        progressDialog = Dialog(this)
//        window.setBackgroundDrawableResource(R.color.window_bg)
//        binding.root.fitsSystemWindows = false
//        binding.root.setPadding(
//            resources.getDimension(R.dimen.margin_large).toInt(),
//            resources.getDimension(R.dimen.margin_large).toInt() * 2,
//            resources.getDimension(R.dimen.margin_large).toInt(),
//            resources.getDimension(R.dimen.margin_large).toInt()
//        )
    }

    override fun onResume() {
        super.onResume()
        progressDialog!!.dismiss()
        binding.root.visibility = View.VISIBLE
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        invalidateOptionsMenu()
    }

    protected fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window?.decorView?.rootView?.windowToken, 0)
    }

    fun getAppTheme(): Int {
        return when (sharedPreferences.fontSize) {
            "small" -> R.style.MyAppTheme_NoActionBar_SmallText
            "large" -> R.style.MyAppTheme_NoActionBar_LargeText
            else -> R.style.MyAppTheme_NoActionBar
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

    private fun verifyAvailableNetwork(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    override fun onStart() {
        super.onStart()
        if (!verifyAvailableNetwork()) {
            showAlert(
                getString(R.string.internet_message),
                getString(R.string.internet_title)
            ) { finish() }
        }
        LocaleHelper.setLocale(this, "ar");
    }

    private fun setAnimation() {
        progressDialog = Dialog(this)
        progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog!!.setContentView(R.layout.custom_dialog_progress)

/* Custom setting to change TextView text,Color and Text Size according to your Preference*/

        val progressTv = progressDialog!!.findViewById(R.id.progress_tv) as TextView
        progressTv.visibility = View.GONE
        progressTv.text = resources.getString(R.string.loading)
        progressTv.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryLight))
        progressTv.textSize = 19F

        progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog!!.setCancelable(false)
    }

    override fun onPause() {
        super.onPause()
//        clearClipBoard()
        progressDialog!!.show()
        binding.root.visibility = View.GONE
    }

    private fun clearClipBoard() {
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", " ")
        clipboard.setPrimaryClip(clip)
    }

    val actionMode = object : ActionMode.Callback {
        override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
            return false
        }

        override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
            return false
        }

        override fun onDestroyActionMode(p0: ActionMode?) {

        }
    }

    //region  router
    protected fun getTheNextActivityFromSplash(): Class<Activity> {
        if (!sharedPreferences.isIntroSkipped)
            return IntroActivity::class.java as Class<Activity>

        return getTheNextActivityFromIntro()
    }

    protected fun getTheNextActivityFromIntro(): Class<Activity> {
        if (sharedPreferences.mobile.isEmpty())
            return SignupActivity::class.java as Class<Activity>

        return getTheNextActivityFromSignup()
    }

    fun getTheNextActivityFromSignup(): Class<Activity> {
        if (sharedPreferences.isAuthenticated && sharedPreferences.isSyndicateMember)
            return SehaHomeActivity::class.java as Class<Activity> //TODO syndicate home

        return SehaHomeActivity::class.java as Class<Activity> //TODO neqabty home
    }
    //endregion

    //region progressDialog
    fun showProgressDialog() {
        if (progressDialog!!.isShowing)
            return
        progressDialog!!.show()
    }

    fun hideProgressDialog() {
        if (progressDialog!!.isShowing)
            progressDialog!!.hide()
    }
    //endregion
}