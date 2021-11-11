package com.neqabty.yodawy.modules

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.neqabty.yodawy.R

class ProductDetailsActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        toolbar = findViewById<Toolbar>(R.id.product_custom_toolbar)
        toolbar.findViewById<ImageView>(R.id.back_btn).setOnClickListener { finish() }
        setSupportActionBar(toolbar)
        supportActionBar?.elevation = 0.0f
    }
}