package com.neqabty.news.core.ui
//
//import android.annotation.SuppressLint
//import android.content.Intent
//import android.content.pm.ActivityInfo
//import android.os.Bundle
//import android.view.MenuItem
//import android.view.View
//import android.view.inputmethod.InputMethodManager
//import android.widget.TextView
//import androidx.appcompat.app.AlertDialog
//import androidx.appcompat.app.AppCompatActivity
//import androidx.appcompat.widget.Toolbar
//import com.neqabty.news.R
//import com.neqabty.news.modules.home.presentation.view.homescreen.HomeActivity
//
//abstract class BaseActivity<B : ViewBinding> : AppCompatActivity(){
//
//    lateinit var binding: B
//    abstract fun getViewBinding(): B
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
//        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//        binding = getViewBinding()
//        setSupportActionBar(binding.root.findViewById(R.id.toolbar))
//        binding.root.findViewById<Toolbar>(R.id.toolbar)?.setNavigationOnClickListener{onBackPressed()}
//        window.setBackgroundDrawableResource(R.color.colorPrimaryDark)
//        binding.root.fitsSystemWindows = true
//    }
//
//    override fun onResume() {
//        super.onResume()
//        invalidateOptionsMenu()
//    }
//
//    protected fun hideKeyboard() {
//        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(window?.decorView?.rootView?.windowToken, 0)
//    }
//
//    protected fun updateCartOptionsMenu(cartMenuItem: MenuItem){
//        cartMenuItem.actionView.setOnClickListener {
//            startActivity(Intent(this, HomeActivity::class.java))
//        }
//        cartMenuItem.actionView.findViewById<TextView>(R.id.tv_count).visibility =
//            if (Constants.cartItems.size == 0 && Constants.imageList.size == 0) View.INVISIBLE else View.VISIBLE
//        cartMenuItem.actionView.findViewById<TextView>(R.id.tv_count).text =
//            getCartCounter()
//    }
//
//    private fun getCartCounter(): String{
//        return Math.max(Constants.cartItems.size, Constants.imageList.size).toString()
//    }
//
//    //region Alerts//
//    protected fun showClearCartConfirmationAlert(okCallback: () -> Unit = {}, cancelCallback: () -> Unit = {}) {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle(getString(R.string.alert_title))
//        builder.setMessage(getString(R.string.will_clear_cart))
//        builder.setCancelable(false)
//        builder.setPositiveButton(getString(R.string.alert_ok)) { dialog, which ->
//            okCallback.invoke()
//            dialog.dismiss()
//        }
//        builder.setNegativeButton(getString(R.string.alert_cancel)) { dialog, which ->
//            cancelCallback.invoke()
//            dialog.dismiss()
//        }
//        builder.show()
//    }
//    //endregion
//
//    //region toolbar//
//    @SuppressLint("ResourceAsColor")
//    fun setupToolbar(titleResId: Int, showUp: Boolean = true, show: Boolean = true) {
//        setupToolbar(getString(titleResId), showUp, show)
//    }
//
//    @SuppressLint("ResourceAsColor")
//    fun setupToolbar(title: String, showUp: Boolean = true, show: Boolean = true) {
//        when (show) {
//            true -> {
//                supportActionBar?.show()
//                supportActionBar?.title = title
//                supportActionBar?.setDisplayHomeAsUpEnabled(showUp)
//            }
//            false -> supportActionBar?.hide()
//        }
//    }
//    //endregion//
//}