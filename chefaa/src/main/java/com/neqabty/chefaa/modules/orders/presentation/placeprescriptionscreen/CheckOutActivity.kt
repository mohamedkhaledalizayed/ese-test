package com.neqabty.chefaa.modules.orders.presentation.placeprescriptionscreen

import android.R.attr.path
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.lifecycle.lifecycleScope
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.data.Cart
import com.neqabty.chefaa.core.data.Constants
import com.neqabty.chefaa.core.data.Constants.cart
import com.neqabty.chefaa.core.data.Constants.selectedAddress
import com.neqabty.chefaa.core.ui.BaseActivity
import com.neqabty.chefaa.core.utils.FileUtils
import com.neqabty.chefaa.core.utils.Status
import com.neqabty.chefaa.databinding.CehfaaActivityCheckOutBinding
import com.neqabty.chefaa.modules.CartAdapter
import com.neqabty.chefaa.modules.PhotosAdapter
import com.neqabty.chefaa.modules.address.presentation.view.adressscreen.AddressesActivity
import com.neqabty.chefaa.modules.home.presentation.homescreen.ChefaaHomeActivity
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException


@AndroidEntryPoint
class CheckOutActivity : BaseActivity<CehfaaActivityCheckOutBinding>() {
    private val placeOrderViewModel: PlaceOrderViewModel by viewModels()
    var total: Float = 0.0f
    private lateinit var dialog: AlertDialog
    private val mAdapter = CheckoutCartAdapter()
    private lateinit var photoAdapter: CheckoutPhotosAdapter

    override fun getViewBinding() = CehfaaActivityCheckOutBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.place_order)

        binding.addressType.text = selectedAddress?.title
        binding.addressDetails.text = "شارع ${selectedAddress?.address}, مبنى رقم ${selectedAddress?.buildingNo}, رقم الطابق ${selectedAddress?.floorNo}, شقة رقم ${selectedAddress?.apartmentNo}"

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()


        photoAdapter = CheckoutPhotosAdapter(this)
        updateView()

        binding.cartLt.photosRv.adapter = photoAdapter
        binding.cartLt.productRv.adapter = mAdapter
        placeOrderViewModel.placeImagesResult.observe(this){
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dialog.show()
                    }
                    Status.SUCCESS -> {
                        dialog.dismiss()
                        Toast.makeText(this, getString(R.string.order_is_placed), Toast.LENGTH_LONG).show()
                        cart = Cart()
                        reLaunchHomeActivity(this)
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }


    private fun updateView() {
        ///// checkout btn and Empty view
        if (cart.size == 0){
            binding.clEmptyCart.visibility = View.VISIBLE
            binding.cartLt.checkout.visibility = View.GONE
        }else{
            binding.clEmptyCart.visibility = View.GONE
            binding.cartLt.checkout.visibility = View.VISIBLE
        }

        /////Images recyclerView
        if (cart.imageList.isNotEmpty()) {
            binding.cartLt.photosRv.visibility = View.VISIBLE
            photoAdapter.submitList()
        } else {
            binding.cartLt.llPhotos.visibility = View.GONE
        }

        /////Products recyclerView
        if(cart.productList.isNotEmpty()) {
            binding.cartLt.productRv.visibility = View.VISIBLE
            mAdapter.submitList()
        } else {
            binding.cartLt.llProducts.visibility = View.GONE
        }


        if(cart.note != null){
            binding.cartLt.noteTv.visibility = View.VISIBLE
            binding.cartLt.noteTv.setText(cart.note!!.note)
        } else {
            binding.cartLt.clNote.visibility = View.GONE
        }
    }

    fun checkOut(view: View) {
        lifecycleScope.launch(Dispatchers.IO) {
            cart.imageList.map {
                it.image = Base64.encodeToString(File(it.imageUri!!.path).readBytes(), Base64.DEFAULT)
            }
            placeOrderViewModel.placePrescriptionImages(selectedAddress?.id!!)
        }
    }
    @NonNull
    private suspend fun prepareFileBase64(
        fileUri: Uri
    ): String {
        val imagefile = FileUtils.getFile(this, fileUri)
        var fis: FileInputStream? = null
        try {
            fis = FileInputStream(imagefile)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        val bm: Bitmap = BitmapFactory.decodeStream(fis)
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

}