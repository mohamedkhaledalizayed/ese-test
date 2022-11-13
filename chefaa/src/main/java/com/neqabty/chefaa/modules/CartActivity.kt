package com.neqabty.chefaa.modules

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.neqabty.chefaa.R
import com.neqabty.chefaa.core.data.Constants
import com.neqabty.chefaa.core.data.Constants.cart
import com.neqabty.chefaa.core.ui.BaseActivity
import com.neqabty.chefaa.databinding.ChefaaActivityCartBinding
import com.neqabty.chefaa.modules.address.presentation.view.adressscreen.AddressesActivity
import com.neqabty.chefaa.modules.home.presentation.homescreen.ChefaaHomeActivity
import com.neqabty.chefaa.modules.orders.domain.entities.OrderItemsEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : BaseActivity<ChefaaActivityCartBinding>() {

    override fun getViewBinding() = ChefaaActivityCartBinding.inflate(layoutInflater)
    private val mAdapter = CartAdapter()
    private lateinit var photoAdapter: PhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.cart)

        photoAdapter = PhotosAdapter(this)
        updateView()

        photoAdapter.onItemClickListener = object :
            PhotosAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: Int) {
                cart.imageList.removeAt(id)
                updateView()
            }
        }

        binding.cartLt.photosRv.adapter = photoAdapter
        binding.cartLt.productRv.adapter = mAdapter
        (binding.cartLt.productRv.adapter as CartAdapter).registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                super.onItemRangeChanged(positionStart, itemCount)
                if (itemCount == 0)
                    updateView()
            }
        })

        binding.cartLt.deleteNote.setOnClickListener{
            cart.note = null
            binding.cartLt.noteTv.setText("")
            updateView()
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
        // save note to cart
        if (!binding.cartLt.noteTv.text.toString().isNullOrBlank()) {
            cart.note = OrderItemsEntity(
                type = Constants.ITEMTYPES.NOTE.typeName,
                quantity = 1,
                image = "",
                note = binding.cartLt.noteTv.text.toString(),
                productId = -1,
                productEntity = null,
                imageUri = null
            )
        }
        startActivity(Intent(this, AddressesActivity::class.java))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        reLaunchHomeActivity(this)
    }
}