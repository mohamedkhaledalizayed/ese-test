package com.neqabty.yodawy.modules.address.presentation.view.homescreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.neqabty.yodawy.R
import com.neqabty.yodawy.modules.CartActivity
import com.neqabty.yodawy.modules.products.presentation.view.productscreen.SearchActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById<Toolbar>(R.id.home_custom_toolbar)
//        cart.setOnClickListener {  startActivity(Intent(this, CartScreen::class.java)) }
        setSupportActionBar(toolbar)
        supportActionBar?.elevation = 0.0f
        toolbar.findViewById<ImageView>(R.id.back_btn).setOnClickListener { finish() }
        toolbar.findViewById<FrameLayout>(R.id.cart).setOnClickListener { startActivity(Intent(this, CartActivity::class.java)) }
    }

    fun findMedications(view: View) {startActivity(Intent(this, SearchActivity::class.java))}
    fun orders(view: View) {}
}