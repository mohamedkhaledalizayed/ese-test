package com.neqabty.yodawy.modules.products.presentation.view.productscreen

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants
import com.neqabty.yodawy.core.data.Constants.imageList
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.databinding.ActivityProductDetailsBinding
import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity

class ProductDetailsActivity : BaseActivity<ActivityProductDetailsBinding>() {

    override fun getViewBinding() = ActivityProductDetailsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val productItem = intent.extras?.getParcelable<ProductEntity>("product")!!

        setupToolbar(title = productItem.name)
        binding.add.setOnClickListener {
            if (imageList.isNotEmpty()){
                showAlert()
            }else{
                Constants.cartItems.addOrIncrement(productItem)
                Toast.makeText(this, "تمت الاضافة بنجاح", Toast.LENGTH_LONG).show()
                finish()
            }
        }


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

    private fun showAlert() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("تنبيه")
        builder.setMessage("message")// TODO mona please add suitable arabic message
        builder.setCancelable(false)
        builder.setPositiveButton("موافق") { dialog, which ->
            imageList.clear()
            dialog.dismiss()
        }
        builder.setNegativeButton("غير موافق") { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

}

fun MutableList<Pair<ProductEntity, Int>>.addOrIncrement(productItem: ProductEntity) {
    var index = -1
    this.mapIndexed { ind, productEntity ->
        if (productEntity.first.id == productItem.id) {
            Pair(productEntity.first, productEntity.second + 1)
            index = ind
        } else
            productEntity
    }
    if (index == -1)
        this.add(Pair(productItem, 1))
}
