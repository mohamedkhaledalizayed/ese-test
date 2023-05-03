package com.neqabty.chefaa.modules.products.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.neqabty.chefaa.core.data.Constants
import com.neqabty.chefaa.core.data.Constants.cart
import com.neqabty.chefaa.core.ui.BaseActivity
import com.neqabty.chefaa.databinding.ChefaaActivityProductDetailsBinding
import com.neqabty.chefaa.modules.CartActivity
import com.neqabty.chefaa.modules.orders.domain.entities.OrderItemsEntity
import com.neqabty.chefaa.modules.products.domain.entities.ProductEntity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ProductDetailsActivity : BaseActivity<ChefaaActivityProductDetailsBinding>() {

    override fun getViewBinding() = ChefaaActivityProductDetailsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val productItem = intent.extras?.getParcelable<ProductEntity>("product")!!

        binding.backBtn.setOnClickListener { finish() }
        binding.cart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
        binding.add.setOnClickListener {
            binding.increaseDecrease.visibility = View.VISIBLE
            addBtnLogic(productItem)
        }
        getIndexInProductsPair(productItem)
        binding.increase.setOnClickListener {
            val index = cart.productList.addOrIncrement(productItem)
            binding.quantity.text = "${cart.productList[index].quantity}"
        }

        binding.decrease.setOnClickListener {
            val index = cart.productList.removeOrDecrement(productItem)
            if (index == -1) {
                binding.add.visibility = View.VISIBLE
                binding.increaseDecrease.visibility = View.GONE
            } else {
                binding.quantity.text = "${cart.productList[index].quantity}"
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

        binding.medicationTitle.text = productItem.titleAr
        binding.medicationPrice.text = "${productItem.price} جنيه"

    }

    private fun addBtnLogic(productItem: ProductEntity) {
        addToCart(productItem)
    }

    private fun addToCart(productItem: ProductEntity) {
        cart.productList.addOrIncrement(productItem)
    }

    private fun getIndexInProductsPair(productItem: ProductEntity): Int {
        var index = 0
        cart.productList.mapIndexed { ind, item ->
            if (item.productId == productItem.id) {
                index = ind
                binding.quantity.text = "${item.quantity}"
                binding.increaseDecrease.visibility = View.VISIBLE
            }
        }
        return index
    }

}


fun MutableList<OrderItemsEntity>.addOrIncrement(productItem: ProductEntity): Int {
    var index = -1
    cart.productList.mapIndexed { ind, it ->
        if (it.productId == productItem.id) {
            cart.productList[ind] = it.copy(quantity = it.quantity + 1)
            index = ind
        }
    }
    if (index == -1) {
        cart.productList.add(
            OrderItemsEntity(
                type = Constants.ITEMTYPES.PRODUCT.typeName,
                quantity = 1,
                image = "",
                note = "",
                productId = productItem.id,
                productEntity = productItem,
                imageUri = null
            )
        )
    }
    return index
}

fun MutableList<OrderItemsEntity>.removeOrDecrement(productItem: ProductEntity): Int {
    var index = -1
    cart.productList.mapIndexed { ind, orderItemsEntity ->
        if (orderItemsEntity.productId == productItem.id) {
            cart.productList[ind] = orderItemsEntity.copy(quantity = orderItemsEntity.quantity - 1)
            index = ind
        }
    }
    if (cart.productList[index].quantity == 0){
        cart.productList.removeAt(index)
        index = -1
    }
    return index
}