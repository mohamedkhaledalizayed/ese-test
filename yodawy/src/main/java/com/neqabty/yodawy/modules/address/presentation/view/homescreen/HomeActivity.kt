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
import com.neqabty.yodawy.databinding.ActivityHomeBinding
import com.neqabty.yodawy.modules.CartActivity
import com.neqabty.yodawy.modules.orders.presentation.view.orderstatusscreen.OrdersActivity
import com.neqabty.yodawy.modules.products.presentation.view.productscreen.SearchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    private val homeViewModel: HomeViewModel by viewModels()
    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.orders_history)
    }

    fun findMedications(view: View) {
        startActivity(Intent(this, SearchActivity::class.java))
    }

    fun orders(view: View) {
        startActivity(Intent(this, OrdersActivity::class.java))
    }

    fun uploadImage(view: View) {
        if (cartItems.isNotEmpty()){
            showAlert()
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

    private fun showAlert() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("تنبيه")
        builder.setMessage("message")// TODO mona please add suitable arabic message
        builder.setCancelable(false)
        builder.setPositiveButton("موافق") { dialog, which ->
        cartItems.clear()
            selectImage()
            dialog.dismiss()
        }
        builder.setNegativeButton("غير موافق") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val uri: Uri = data?.data!!
                if (imageList.isEmpty()) {
                    imageList.add(0, Uri.EMPTY)
                }
                imageList.add(uri)
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)

        val cartItem = menu.findItem(R.id.menu_item_cart)
        cartItem.actionView.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        cartItem.actionView.findViewById<TextView>(R.id.tv_count).visibility =
            if (Constants.cartItems.size == 0) View.INVISIBLE else View.VISIBLE
        cartItem.actionView.findViewById<TextView>(R.id.tv_count).text =
            Constants.cartItems.size.toString()
        return super.onCreateOptionsMenu(menu)
    }
}