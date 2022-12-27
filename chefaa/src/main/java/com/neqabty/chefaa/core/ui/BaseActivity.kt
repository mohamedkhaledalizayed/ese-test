package com.neqabty.chefaa.core.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.data.Constants
import com.neqabty.chefaa.modules.CartActivity
import com.neqabty.chefaa.modules.home.presentation.homescreen.ChefaaHomeActivity


abstract class BaseActivity<B : ViewBinding> : AppCompatActivity(){

    lateinit var binding: B
    abstract fun getViewBinding(): B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        binding = getViewBinding()
        setSupportActionBar(binding.root.findViewById(R.id.toolbar))
        binding.root.findViewById<Toolbar>(R.id.toolbar)?.setNavigationOnClickListener{onBackPressed()}
        window.setBackgroundDrawableResource(R.color.white)
        binding.root.fitsSystemWindows = true
    }

    override fun onResume() {
        super.onResume()
        binding.root.visibility = View.VISIBLE
        invalidateOptionsMenu()
    }

    override fun onPause() {
        super.onPause()
        binding.root.visibility = View.GONE
    }

    protected fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window?.decorView?.rootView?.windowToken, 0)
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
    //region Alerts//
    protected fun showClearCartConfirmationAlert(okCallback: () -> Unit = {}, cancelCallback: () -> Unit = {}) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(getString(R.string.will_clear_cart))
        builder.setCancelable(false)
        builder.setPositiveButton(getString(R.string.alert_ok)) { dialog, which ->
            okCallback.invoke()
            dialog.dismiss()
        }
        builder.setNegativeButton(getString(R.string.alert_cancel)) { dialog, which ->
            cancelCallback.invoke()
            dialog.dismiss()
        }
        builder.show()
    }
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

    fun checkGPS(): Boolean{
         val manager = getSystemService( Context.LOCATION_SERVICE ) as LocationManager
        return manager.isProviderEnabled( LocationManager.GPS_PROVIDER )
    }

    fun buildAlertMessageNoGps() {
        val builder = AlertDialog.Builder(this)
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
            .withListener(object : MultiplePermissionsListener{
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
        val builder = AlertDialog.Builder(this@BaseActivity)
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
    //endregion//
}
/*
order options:
search and add to cart
upload rx
type a manual request


from only one screen to place order
user can place order by  all three options together and should at least contain only one option

 */