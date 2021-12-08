package com.neqabty.yodawy.modules.address.presentation.view.homescreen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.github.dhaval2404.imagepicker.ImagePicker
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants
import com.neqabty.yodawy.core.data.Constants.cartItems
import com.neqabty.yodawy.core.data.Constants.imageList
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.core.utils.Status
import com.neqabty.yodawy.databinding.ActivityHomeBinding
import com.neqabty.yodawy.modules.CartActivity
import com.neqabty.yodawy.modules.orders.presentation.view.orderstatusscreen.OrdersActivity
import com.neqabty.yodawy.modules.products.presentation.view.productscreen.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    private val homeViewModel: HomeViewModel by viewModels()
    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)
    private lateinit var dialog: android.app.AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.yodawy_home_title)
        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()

        Constants.userNumber = intent.extras!!.getString("user_number", "")
        Constants.mobileNumber = intent.extras!!.getString("mobile_number", "")
        Constants.jwt = intent.extras!!.getString("jwt", Constants.jwt)
        Constants.FIXED_TOKEN = intent.extras!!.getString("fixed_token", Constants.FIXED_TOKEN)
        Constants.YODAWY_URL = intent.extras!!.getString("url", Constants.YODAWY_URL)

        homeViewModel.getUser(Constants.userNumber, Constants.mobileNumber)

        homeViewModel.user.observe(this){

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dialog.show()
                    }
                    Status.SUCCESS -> {
                        dialog.dismiss()
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                    }
                }
            }

        }
    }

    fun findMedications(view: View) {
        startActivity(Intent(this, SearchActivity::class.java))
    }

    fun orders(view: View) {
        startActivity(Intent(this, OrdersActivity::class.java))
    }

    fun uploadImage(view: View) {
        if (cartItems.isNotEmpty()){
            showClearCartConfirmationAlert(okCallback = {
                cartItems.clear()
                invalidateOptionsMenu()
                selectImage()
            })
        }else{
           selectImage()
        }
    }

    private fun selectImage(){
        ImagePicker.with(this)
            .crop()                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val uri: Uri = data?.data!!
                imageList.add(uri)
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)

        val cartItem = menu.findItem(R.id.menu_item_cart)
        updateCartOptionsMenu(cartItem)

        return super.onCreateOptionsMenu(menu)
    }
}