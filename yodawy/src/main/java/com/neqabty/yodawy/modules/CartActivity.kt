package com.neqabty.yodawy.modules

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants.cartItems
import com.neqabty.yodawy.core.data.Constants.imageList
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.databinding.ActivityCartBinding
import com.neqabty.yodawy.modules.address.presentation.view.adressscreen.AddressesActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class CartActivity : BaseActivity<ActivityCartBinding>() {

    override fun getViewBinding() = ActivityCartBinding.inflate(layoutInflater)
    private val mAdapter = CartAdapter()
    private lateinit var photoAdapter: PhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.cart)

        photoAdapter = PhotosAdapter(this)
        binding.photosRecycler.adapter = photoAdapter
        photoAdapter.onItemClickListener = object :
            PhotosAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: Int) {
                imageList.removeAt(id)
                updateView()
            }
        }

        binding.cartRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            CartAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: Int) {

            }

            override fun notifyUi() {
                updateView()
            }

            override fun updateTotal() {
                checkTotal()
            }
        }

        updateView()
        checkTotal()
    }

    private fun checkTotal(){
        var total = 0.0
        if (cartItems.size > 0){
            for (item in cartItems){
                total += (item.first.regularPrice * item.second)
            }
            binding.checkout.text = getString(R.string.go_to_checkout) + "  ${total.roundToInt()}"
        }
    }

    private fun updateView() {
        ///// checkout btn and Empty view
        if (cartItems.isEmpty() && imageList.isEmpty()){
            binding.clEmptyCart.visibility = View.VISIBLE
            binding.checkout.visibility = View.GONE
        }else{
            binding.clEmptyCart.visibility = View.GONE
            binding.checkout.visibility = View.VISIBLE
        }

        /////Images recyclerView
        if (imageList.isNotEmpty()) {
            binding.llImagesHolder.visibility = View.VISIBLE
            photoAdapter.submitList(imageList)
        }

        /////Products recyclerView
        if(cartItems.isNotEmpty()) {
            mAdapter.submitList(cartItems)
        }
    }

    fun checkOut(view: View) {
        startActivity(Intent(this, AddressesActivity::class.java))
    }

}