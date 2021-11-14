package com.neqabty.yodawy.core.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants
import com.neqabty.yodawy.modules.CartActivity

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
            if (Constants.cartItems.size == 0 && Constants.imageList.size == 0) View.INVISIBLE else View.VISIBLE
        cartMenuItem.actionView.findViewById<TextView>(R.id.tv_count).text =
            getCartCounter()
    }

    private fun getCartCounter(): String{
        return Math.max(Constants.cartItems.size, Constants.imageList.size).toString()
    }
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