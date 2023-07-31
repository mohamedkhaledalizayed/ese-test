package com.neqabty.healthcare.core.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.*
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.neqabty.healthcare.R
import com.neqabty.healthcare.chefaa.cart.CartActivity
import com.neqabty.healthcare.chefaa.home.view.ChefaaHomeActivity
import com.neqabty.healthcare.contactus.NumbersDialog
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.core.home_general.GeneralHomeActivity
import com.neqabty.healthcare.core.home_syndicates.view.SyndicatesHomeActivity
import com.neqabty.healthcare.core.utils.LocaleHelper
import com.neqabty.healthcare.onboarding.contact.view.ContactCheckMemberActivity
import com.neqabty.healthcare.onboarding.intro.view.IntroActivity
import com.neqabty.healthcare.onboarding.signup.view.SignupActivity
import javax.inject.Inject


abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: PreferencesHelper
    lateinit var binding: B
    abstract fun getViewBinding(): B
    var progressDialog: Dialog? = null
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(base))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAnimation()
        setTheme(getAppTheme())
        binding = getViewBinding()
        setSupportActionBar(binding.root.findViewById(R.id.layout_toolbar))
        binding.root.findViewById<Toolbar>(R.id.layout_toolbar)
            ?.setNavigationOnClickListener { onBackPressed() }

        window.setBackgroundDrawableResource(R.color.window_bg)

        Constants.isFirebaseTokenUpdated.observe(this, Observer {
            if (it.isNotBlank()){
                if (!it.equals(sharedPreferences.firebaseToken)) // Token has been changed
                {
                    sharedPreferences.firebaseToken = it
                    Log.i("toooooken", it)
//                    if (sharedPreferences.user.isNotBlank()) { // verified
//                        mainViewModel.login(sharedPref.mobile, sharedPref.user, it, sharedPref)
//                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
//        progressDialog!!.dismiss()
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
        LocaleHelper.setLocale(this, "ar")
    }

    private fun setAnimation() {
        progressDialog = Dialog(this)
        progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog!!.setContentView(R.layout.custom_dialog_progress)

//        val progressTv = progressDialog!!.findViewById(R.id.progress_tv) as TextView
//        progressTv.visibility = View.GONE
//        progressTv.text = resources.getString(R.string.loading)
//        progressTv.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryLight))
//        progressTv.textSize = 19F
        progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog!!.setCancelable(false)
    }

    override fun onPause() {
        super.onPause()
//        clearClipBoard()
//        progressDialog!!.show()
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

    protected fun updateCartOptionsMenu(cartMenuItem: MenuItem){
        cartMenuItem.actionView.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        cartMenuItem.actionView.findViewById<TextView>(R.id.tv_count).visibility =
            if (Constants.cart.size == 0 ) View.INVISIBLE else View.VISIBLE
        cartMenuItem.actionView.findViewById<TextView>(R.id.tv_count).text =
            getCartCounter()
    }

    private fun getCartCounter(): String{
        return Constants.cart.getChildrenCounter().toString()
    }

    protected fun reLaunchHomeActivity(context: Context){
        val bundle = Bundle()
        bundle.putString("user_number", Constants.userNumber)
        bundle.putString("mobile_number", Constants.mobileNumber)
        bundle.putString("country_code", Constants.countryCode)
        bundle.putString("national_id", Constants.nationalID)
        bundle.putString("name", Constants.name)
        bundle.putString("jwt", Constants.jwt)
        val intent = Intent(context, ChefaaHomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtras(bundle)
        startActivity(intent)
        finish()
    }
    //region  onboarding router
    protected fun getTheNextActivityFromSplash(): Class<Activity> {
        if (!sharedPreferences.isIntroSkipped)
            return IntroActivity::class.java as Class<Activity>

        return getTheNextActivityFromIntro()
    }

    protected fun getTheNextActivityFromIntro(): Class<Activity> {
        if (sharedPreferences.isSkippedToHome || sharedPreferences.isContactActiveSubscriber)
            return getHomeActivity()

        if(sharedPreferences.isContactSubscriber) //did the OCR, go to contact check member
            return ContactCheckMemberActivity::class.java as Class<Activity>

        if(sharedPreferences.isAuthenticated)
            return ContactCheckMemberActivity::class.java as Class<Activity>

        return SignupActivity::class.java as Class<Activity>
    }

    fun getHomeActivity(): Class<Activity> {
        sharedPreferences.isSkippedToHome = true
        if (sharedPreferences.isAuthenticated && sharedPreferences.isSyndicateMember)
            return SyndicatesHomeActivity::class.java as Class<Activity> //TODO syndicate home

        return GeneralHomeActivity::class.java as Class<Activity> //TODO neqabty home
    }
    //endregion m

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



    fun checkGPS(): Boolean{
        val manager = getSystemService( Context.LOCATION_SERVICE ) as LocationManager
        return manager.isProviderEnabled( LocationManager.GPS_PROVIDER )
    }

    fun buildAlertMessageNoGps() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setMessage("يبدو أن GPS الخاص بك غير مفعل ، يرجى تفعيله.")
            .setCancelable(false)
            .setPositiveButton(
                "تاكيد"
            ) { _, id -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
        val alert = builder.create()
        alert.show()
    }

    fun requestPermissions() {
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {
                    // this method is called when all permissions are granted
                    if (multiplePermissionsReport.areAllPermissionsGranted()) {
                        // do you work now
                    }
                    // check for permanent denial of any permission
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied) {
                        // permission is denied permanently, we will show user a dialog message.
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    list: List<PermissionRequest?>?,
                    permissionToken: PermissionToken
                ) {
                    // this method is called when user grants some permission and denies some of them.
                    permissionToken.continuePermissionRequest()
                }
            }).withErrorListener {
                // we are displaying a toast message for error message.
                Toast.makeText(applicationContext, "Error occurred! ", Toast.LENGTH_SHORT).show()
            } // below line is use to run the permissions on same thread and to check the permissions
            .onSameThread().check()
    }

    private fun showSettingsDialog() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this@BaseActivity)
        builder.setTitle("بحاجة إلى أذونات")
        builder.setCancelable(false)
        builder.setMessage("يحتاج هذا التطبيق إلى إذن لاستخدام هذه الميزة. يمكنك منحهم في إعدادات التطبيق.")
        builder.setPositiveButton("اذهب للاعدادات\n") { dialog: DialogInterface, which: Int ->
            dialog.cancel()
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri: Uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivityForResult(intent, 101)
        }
        try {
            builder.show()
        }catch (e: Exception){

        }
    }

    protected fun closeApp() {
        val fm: FragmentManager = supportFragmentManager
        val dialog = ExitDialog()
        dialog.show(fm, "")
        dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth)
    }

}