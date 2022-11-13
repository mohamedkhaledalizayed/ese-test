package com.neqabty.chefaa.core.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
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
        window.setBackgroundDrawableResource(R.color.colorPrimaryDark)
        binding.root.fitsSystemWindows = true
    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
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