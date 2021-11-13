package com.neqabty.yodawy.modules.products.presentation.view.productscreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.databinding.ActivityProductDetailsBinding
import com.neqabty.yodawy.modules.CartActivity
import com.neqabty.yodawy.modules.products.data.model.search.Data

class ProductDetailsActivity : BaseActivity<ActivityProductDetailsBinding>() {

    override fun getViewBinding() = ActivityProductDetailsBinding.inflate(layoutInflater)
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        toolbar = findViewById<Toolbar>(R.id.product_custom_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.elevation = 0.0f
        toolbar.findViewById<ImageView>(R.id.back_btn).setOnClickListener { finish() }
        toolbar.findViewById<FrameLayout>(R.id.cart).setOnClickListener { startActivity(Intent(this, CartActivity::class.java)) }

        val productItem = intent.extras?.getParcelable<Data>("product")!!

        binding.add.setOnClickListener {
            Toast.makeText(this, "تمت الاضافة بنجاح", Toast.LENGTH_LONG).show()
            finish()
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


}