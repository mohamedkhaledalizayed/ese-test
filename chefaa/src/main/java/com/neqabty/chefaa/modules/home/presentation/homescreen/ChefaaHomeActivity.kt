package com.neqabty.chefaa.modules.home.presentation.homescreen

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
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.data.Constants
import com.neqabty.chefaa.core.data.Constants.cart
import com.neqabty.chefaa.core.ui.BaseActivity
import com.neqabty.chefaa.databinding.ChefaaActivityHomeBinding
import com.neqabty.chefaa.modules.orders.domain.entities.OrderItemsEntity
import com.neqabty.chefaa.modules.orders.presentation.orderbynote.OrderByNoteActivity
import com.neqabty.chefaa.modules.products.domain.entities.ProductEntity
import com.neqabty.chefaa.modules.products.presentation.SearchActivity
//import com.neqabty.chefaa.modules.CartActivity
//import com.neqabty.chefaa.modules.orders.presentation.view.orderstatusscreen.OrdersActivity
//import com.neqabty.chefaa.modules.products.presentation.view.productscreen.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class ChefaaHomeActivity : BaseActivity<ChefaaActivityHomeBinding>() {
    private val homeViewModel: HomeViewModel by viewModels()
    override fun getViewBinding() = ChefaaActivityHomeBinding.inflate(layoutInflater)
    private lateinit var dialog: android.app.AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.orders_history)

        Constants.userNumber = intent.extras!!.getString("user_number", "")
        Constants.mobileNumber = intent.extras!!.getString("mobile_number", "")
        Constants.jwt = intent.extras!!.getString("jwt", Constants.jwt)

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()

        homeViewModel.userRegistered.observe(this) {
            if (it) {
                dialog.dismiss()
            }
        }

        homeViewModel.registerUser(Constants.mobileNumber, Constants.userNumber, "+20")
    }

    fun findMedications(view: View) {
        startActivity(Intent(this, SearchActivity::class.java))
    }

    fun orders(view: View) {
//        startActivity(Intent(this, OrdersActivity::class.java))
    }

    fun uploadImage(view: View) {
        selectImage()
    }

    fun orderByNote(view: View){
        startActivity(Intent(this,OrderByNoteActivity::class.java))
    }

    private fun selectImage() {
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
                cart.imageList.add(
                    OrderItemsEntity(
                        image = uri.path!!,
                        quantity = 1,
                        type = Constants.ITEMTYPES.IMAGE.typeName,
                        note = "",
                        productId = -1,
                        productEntity = null,
                        imageUri = uri
                    )
                )
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