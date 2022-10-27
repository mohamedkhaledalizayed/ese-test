package com.neqabty.chefaa.modules.products.presentation

import android.os.Bundle
import android.view.View
import com.neqabty.chefaa.core.data.Constants
import com.neqabty.chefaa.core.data.Constants.cart
import com.neqabty.chefaa.core.ui.BaseActivity
import com.neqabty.chefaa.databinding.ActivityProductDetailsBinding
import com.neqabty.chefaa.modules.orders.domain.entities.OrderItemsEntity
import com.neqabty.chefaa.modules.products.domain.entities.ProductEntity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ProductDetailsActivity : BaseActivity<ActivityProductDetailsBinding>() {

    override fun getViewBinding() = ActivityProductDetailsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val productItem = intent.extras?.getParcelable<ProductEntity>("product")!!

        setupToolbar(title = productItem.titleEn)

        // render add btn || + - btn
        if (cart.productList.find { it.productId == productItem.id } != null) {
            binding.increaseDecrease.visibility = View.VISIBLE
            binding.add.visibility = View.GONE
        } else {
            binding.increaseDecrease.visibility = View.GONE
            binding.add.visibility = View.VISIBLE
        }


        binding.add.setOnClickListener {
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
                binding.increaseDecrease.visibility = View.GONE
                binding.add.visibility = View.VISIBLE
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

        binding.medicationTitle.text = productItem.titleEn
        binding.medicationPrice.text = "EGP ${productItem.price.toString()}"

    }

    private fun addBtnLogic(productItem: ProductEntity) {
        addToCart(productItem)
    }

    private fun addToCart(productItem: ProductEntity) {
        cart.productList.addOrIncrement(productItem)
        binding.increaseDecrease.visibility = View.VISIBLE
        binding.add.visibility = View.GONE
    }

    private fun getIndexInProductsPair(productItem: ProductEntity): Int {
        var index = 0
        cart.productList.mapIndexed { ind, item ->
            if (item.productId == productItem.id) {
                index = ind
                binding.increaseDecrease.visibility = View.VISIBLE
                binding.add.visibility = View.GONE
                binding.quantity.text = "${item.quantity}"
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
                type = Constants.ITEMTYPES.PRODUCT.name,
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