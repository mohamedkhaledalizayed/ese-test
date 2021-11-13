package com.neqabty.yodawy.modules.products.presentation.view.productscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.neqabty.yodawy.R
import com.neqabty.yodawy.modules.CartActivity
import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        toolbar = findViewById<Toolbar>(R.id.product_custom_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.elevation = 0.0f
        toolbar.findViewById<ImageView>(R.id.back_btn).setOnClickListener { finish() }
        toolbar.findViewById<FrameLayout>(R.id.cart).setOnClickListener { startActivity(Intent(this, CartActivity::class.java)) }

        val productItem = intent.extras?.getParcelable<ProductEntity>("product")!!
    }
}