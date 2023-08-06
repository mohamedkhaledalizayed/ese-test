package com.neqabty.healthcare.chefaa.cart

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.neqabty.healthcare.chefaa.address.presentation.view.adressscreen.AddressesActivity
import com.neqabty.healthcare.core.data.Constants.cart
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ChefaaActivityCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : BaseActivity<ChefaaActivityCartBinding>() {

    override fun getViewBinding() = ChefaaActivityCartBinding.inflate(layoutInflater)
    private val mAdapter = CartAdapter()
    private val mItemsAdapter = CartItemsAdapter()
    private var totalPrice = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }

        binding.continueBtn.setOnClickListener {
            startActivity(Intent(this, AddressesActivity::class.java))
        }

        binding.recyclerView.adapter = mAdapter
        binding.itemsRecyclerView.adapter = mItemsAdapter
        mAdapter.onItemClickListener = object :
            CartAdapter.OnItemClickListener {
            override fun setOnItemClickListener() {
                handleCart()
                updatePrice()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        handleCart()
        updatePrice()
    }

    private fun handleCart() {
        if (cart.size == 0){
            binding.recyclerView.visibility = View.GONE
            binding.itemsDetails.visibility = View.GONE
            binding.continueBtn.visibility = View.GONE
            binding.cartEmpty.visibility = View.VISIBLE
        }else{
            binding.recyclerView.visibility = View.VISIBLE
            binding.itemsDetails.visibility = View.VISIBLE
            binding.continueBtn.visibility = View.VISIBLE
            binding.cartEmpty.visibility = View.GONE
        }
    }

    private fun updatePrice() {
        totalPrice = 0.0
        for (item in cart.productList){
            totalPrice += item.productEntity?.price!!.times(item.quantity.toDouble())
        }

        binding.total.text = "$totalPrice جنيه"
    }

}