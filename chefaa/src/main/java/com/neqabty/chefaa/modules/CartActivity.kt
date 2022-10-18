package com.neqabty.chefaa.modules

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.data.Constants.cartItems
import com.neqabty.chefaa.core.data.Constants.imageList
import com.neqabty.chefaa.core.ui.BaseActivity
import com.neqabty.chefaa.databinding.ActivityCartBinding
import com.neqabty.chefaa.modules.address.presentation.view.adressscreen.AddressesActivity
import dagger.hilt.android.AndroidEntryPoint

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
        }

        updateView()
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