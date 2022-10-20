package com.neqabty.chefaa.modules.products.presentation
//
//import android.os.Bundle
//import android.view.View
//import androidx.appcompat.app.AlertDialog
//import com.neqabty.chefaa.R
////import com.neqabty.chefaa.core.data.Constants.cartItems
//import com.neqabty.chefaa.core.data.Constants.imageList
//import com.neqabty.chefaa.core.ui.BaseActivity
//import com.neqabty.chefaa.databinding.ActivityProductDetailsBinding
////import com.neqabty.chefaa.modules.products.domain.entity.ProductEntity
//import com.squareup.picasso.Callback
//import com.squareup.picasso.Picasso
//
//class ProductDetailsActivity : BaseActivity<ActivityProductDetailsBinding>() {
//
//    override fun getViewBinding() = ActivityProductDetailsBinding.inflate(layoutInflater)
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//
//        val productItem = intent.extras?.getParcelable<ProductEntity>("product")!!
//
//        setupToolbar(title = productItem.name)
//
//        // render add btn || + - btn
//        if (cartItems.find { it.first.id == productItem.id } != null) {
//            binding.increaseDecrease.visibility = View.VISIBLE
//            binding.add.visibility = View.GONE
//        } else {
//            binding.increaseDecrease.visibility = View.GONE
//            binding.add.visibility = View.VISIBLE
//        }
//
//
//        binding.add.setOnClickListener {
//            if (productItem.isLimitedAvailability) {
//                showLimitedAvailabilityAlert(okCallback = { addBtnLogic(productItem) })
//            } else {
//                addBtnLogic(productItem)
//            }
//        }
//        getIndexInProductsPair(productItem)
//        binding.increase.setOnClickListener {
////            val index = getIndexInProductsPair(productItem)
////            cartItems[index].first.quantity += 1
//            val index = cartItems.addOrIncrement(productItem)
//            binding.quantity.text = "${cartItems[index].second}"
//        }
//
//        binding.decrease.setOnClickListener {
//////            val index = getIndexInProductsPair(productItem)
////            if (cartItems[index].first.quantity > 1) {
////                cartItems[index].first.quantity -= 1
////                binding.quantity.text = "${cartItems[index].first.quantity}"
////            } else {
////                //remove this from list
////                cartItems.removeAt(index)
////                binding.increaseDecrease.visibility = View.GONE
////                binding.add.visibility = View.VISIBLE
////            }
//////            val index = cartItems.removeOrDecrement(productItem)
////            if(index==-1){
////                binding.increaseDecrease.visibility = View.GONE
////                binding.add.visibility = View.VISIBLE
////            }else{
////                binding.quantity.text = "${cartItems[index].second}"
////            }
//
//        }
//
//        Picasso.get()
//            .load(productItem.image)
//            .into(binding.medicationImage, object : Callback {
//                override fun onSuccess() {
//                    binding.imageProgress.hide()
//                }
//
//                override fun onError(e: Exception?) {
//                    binding.imageProgress.hide()
//                }
//            })
//
//        binding.medicationTitle.text = productItem.name
//        binding.medicationPrice.text = "EGP ${productItem.salePrice.toString()}"
//
//        when {
//            productItem.outOfStock -> {
//                binding.medicationStatus.visibility = View.INVISIBLE
//                binding.deliveryTime.visibility = View.GONE
//                binding.add.text = "Out of Stock"
//                binding.add.setBackgroundColor(resources.getColor(R.color.gray_yodawy))
//                binding.add.isEnabled = false
//            }
//            productItem.isLimitedAvailability -> {
//                binding.medicationStatus.setImageResource(R.drawable.exclamation)
//                binding.deliveryTime.text = "May not be available"
//            }
//            else -> {
//
//            }
//        }
//    }
//
//    private fun addBtnLogic(productItem: ProductEntity) {
//        if (imageList.isNotEmpty()) {
//            showClearCartConfirmationAlert(okCallback = {
//                imageList.clear()
//                addToCart(productItem)
//            })
//        } else {
//            addToCart(productItem)
//        }
//    }
//
//    private fun addToCart(productItem: ProductEntity) {
//        cartItems.addOrIncrement(productItem)
//        binding.increaseDecrease.visibility = View.VISIBLE
//        binding.add.visibility = View.GONE
//    }
//
//    private fun getIndexInProductsPair(productItem: ProductEntity): Int {
//        var index = 0
//        cartItems.mapIndexed { ind, item ->
//            if (item.first.id == productItem.id) {
//                index = ind
//                binding.increaseDecrease.visibility = View.VISIBLE
//                binding.add.visibility = View.GONE
//                binding.quantity.text = "${item.second}"
//            }
//        }
//        return index
//    }
//
//
//    private fun showLimitedAvailabilityAlert(
//        okCallback: () -> Unit = {},
//        cancelCallback: () -> Unit = {}
//    ) {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle(getString(R.string.alert_title))
//        builder.setMessage(getString(R.string.low_stock_alert))
//        builder.setCancelable(false)
//        builder.setPositiveButton(getString(R.string.alert_ok)) { dialog, which ->
//            okCallback.invoke()
//            dialog.dismiss()
//        }
//        builder.setNegativeButton(getString(R.string.alert_cancel)) { dialog, which ->
//            cancelCallback.invoke()
//            dialog.dismiss()
//        }
//        builder.show()
//    }
//}
//
//fun MutableList<Pair<ProductEntity, Int>>.addOrIncrement(productItem: ProductEntity):Int {
//    var index = -1
//    var iterator = 0
//    val iterable = this.iterator()
//    while (iterable.hasNext()){
//        val it = iterable.next()
//        if(it.first.id == productItem.id){
//            this[iterator] = it.copy(second = it.second+1)
//            index =iterator
//        }
//        iterator+=1
//    }
//    if (index == -1) {
//        this.add(Pair(productItem, 1))
//        index = this.size - 1
//    }
//    return index
//}
//
//fun MutableList<Pair<ProductEntity, Int>>.removeOrDecrement(productItem: ProductEntity) :Int{
//    var index = -1
//    var iterator = 0
//    val iterable = this.iterator()
//    while (iterable.hasNext()){
//        val it = iterable.next()
//        if(it.first.id == productItem.id && it.second > 1){
//            this[iterator] = it.copy(second = it.second-1)
//            index =iterator
//        }else if(it.first.id == productItem.id && it.second == 1){
//            iterable.remove()
//        }
//        iterator+=1
//    }
//    return index
//}
//
//
//fun MutableList<Pair<ProductEntity, Int>>.getChildrenCounter(): Int {
//    var count = 0
//    this.forEach { productPair: Pair<ProductEntity, Int> ->
//        count += productPair.second
//    }
//    return count
//}
//inline fun <T> List<T>.forEachIterable(block: (T) -> Unit) {
//    with(iterator()) { forEach { block(it) } }
//}