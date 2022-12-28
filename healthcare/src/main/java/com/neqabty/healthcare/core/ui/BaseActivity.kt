package com.neqabty.healthcare.core.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.*
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.neqabty.healthcare.IIsolatedService
import com.neqabty.healthcare.IRemoteService
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.core.security.IsolatedService
import com.neqabty.healthcare.core.security.RemoteService
import com.neqabty.healthcare.core.utils.LocaleHelper
import com.neqabty.healthcare.core.utils.disableCopying
import com.neqabty.healthcare.core.utils.forAllChildren
import javax.inject.Inject


abstract class BaseActivity<B : ViewBinding> : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: PreferencesHelper
    lateinit var binding: B
    abstract fun getViewBinding(): B
    private var progressDialog: Dialog? = null

    private lateinit var serviceBinder: IIsolatedService
    private var bServiceBound: Boolean = false

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAnimation()
        setTheme(getAppTheme())
        binding = getViewBinding()
        setSupportActionBar(binding.root.findViewById(R.id.toolbar))
        binding.root.findViewById<Toolbar>(R.id.toolbar)
            ?.setNavigationOnClickListener { onBackPressed() }
        window.setBackgroundDrawableResource(R.color.white)
        binding.root.fitsSystemWindows = true

        (binding.root as ViewGroup).forAllChildren { v ->
            when (v) {
                is EditText -> v.disableCopying()
            }
        }
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
        LocaleHelper.setLocale(this, "ar");


        val intent = Intent(applicationContext, IsolatedService::class.java)
        /*Binding to an isolated service */
        applicationContext.bindService(
            intent,
            mIsolatedServiceConnection,
            BIND_AUTO_CREATE
        )

//        val intentt = Intent(applicationContext, RemoteService::class.java)
//        applicationContext.bindService(intentt, connection, BIND_AUTO_CREATE)
    }

    private fun setAnimation(){
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
        clearClipBoard()
//        progressDialog!!.show()
        binding.root.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
//        unbindService(connection)
    }
    internal fun showAlertDialogAndExitApp(message: String) {

        val alertDialog = androidx.appcompat.app.AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(message)
        alertDialog.setCancelable(false)
        alertDialog.setButton(
            androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, getString(R.string.agree)
        ) { dialog, _ ->
            dialog.dismiss()
            finishAffinity()
        }

        alertDialog.show()
    }

    private fun clearClipBoard(){
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

    private val mIsolatedServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            serviceBinder = IIsolatedService.Stub.asInterface(iBinder)
            bServiceBound = serviceBinder.isMagiskPresent
            if(bServiceBound)
                showAlertDialogAndExitApp(getString(R.string.rooted))
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            bServiceBound = false
        }
    }
    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            val service = IRemoteService.Stub.asInterface(binder)
            try {
                if(service.haveSu() == 1 || service.haveMagicMount() >= 1 || service.haveMagiskHide() >= 1)
                    showAlertDialogAndExitApp(getString(R.string.rooted))
            } catch (e: RemoteException) {
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
        }

        override fun onNullBinding(name: ComponentName) {
        }
    }

}