package com.neqabty.yodawy.modules.products.presentation.view.productscreen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants.cartItems
import com.neqabty.yodawy.core.data.Constants.imageList
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.databinding.ActivityProductDetailsBinding
import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ProductDetailsActivity : BaseActivity<ActivityProductDetailsBinding>() {

    override fun getViewBinding() = ActivityProductDetailsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val productItem = intent.extras?.getParcelable<ProductEntity>("product")!!

        setupToolbar(title = productItem.name)
        binding.add.setOnClickListener {
            if (imageList.isNotEmpty()){
                showClearCartConfirmationAlert(okCallback = {
                    imageList.clear()
                    addToCart(productItem)
                })
            }else{
                addToCart(productItem)
            }
            }

        binding.increase.setOnClickListener {
            val index = getIndexInProductsPair(productItem)
            cartItems[index].first.quantity += 1
            binding.quantity.text = "${cartItems[index].first.quantity}"
        }

        binding.decrease.setOnClickListener {
            val index = getIndexInProductsPair(productItem)
            if (cartItems[index].first.quantity > 1){
                cartItems[index].first.quantity -= 1
                binding.quantity.text = "${cartItems[index].first.quantity}"
            }else{
                //remove this from list
                cartItems.removeAt(index)
                binding.increaseDecrease.visibility = View.GONE
                binding.add.visibility = View.VISIBLE
                Toast.makeText(this, "تم مسح المنتج", Toast.LENGTH_LONG).show()
            }

        }

        Picasso.get()
            .load(productItem.image)
            .into(binding.medicationImage, object : Callback {
                override fun onSuccess() {
                    binding.imageProgress.hide()
                }

                override fun onError(e: Exception?) {
                    binding.imageProgress.hide()
                }
            })

        binding.medicationTitle.text = productItem.name
        binding.medicationPrice.text = "EGP ${productItem.salePrice.toString()}"

        when {
            productItem.outOfStock -> {
                binding.medicationStatus.visibility = View.INVISIBLE
                binding.deliveryTime.visibility = View.GONE
                binding.add.text = "Out of Stock"
                binding.add.setBackgroundColor(resources.getColor(R.color.gray))
                binding.add.isEnabled = false
            }
            productItem.isLimitedAvailability -> {
                binding.medicationStatus.setImageResource(R.drawable.exclamation)
                binding.deliveryTime.text = "May not be available"
            }
            else -> {

            }
        }
    }

    private fun addToCart(productItem: ProductEntity){
        cartItems.addOrIncrement(productItem)
        binding.increaseDecrease.visibility = View.VISIBLE
        binding.add.visibility = View.GONE
    }

    private fun getIndexInProductsPair(productItem: ProductEntity): Int{
        var index = 0
        cartItems.mapIndexed{ ind, item ->
            if (item.first.id == productItem.id){
                index = ind
                binding.increaseDecrease.visibility = View.VISIBLE
                binding.add.visibility = View.GONE
                binding.quantity.text = "${item.first.quantity}"
            }
        }
        return index
    }
}

fun MutableList<Pair<ProductEntity, Int>>.addOrIncrement(productItem: ProductEntity) {
    var index = -1
    this.mapIndexed { ind, productEntity ->//TODO fix this
        if (productEntity.first.id == productItem.id) {
            cartItems[ind].first.quantity += 1
            index = ind
        } else
            productEntity
    }
    if (index == -1)
        this.add(Pair(productItem, 1))
}
