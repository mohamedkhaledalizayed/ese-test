package com.neqabty.superneqabty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neqabty.news.modules.home.presentation.view.homescreen.HomeActivity


class SuperNeqabtyMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_super_neqabty_main)
        startActivity(Intent(this,HomeActivity::class.java))
    }
}