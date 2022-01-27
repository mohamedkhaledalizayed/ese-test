package com.neqabty.superneqabty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.superneqabty.databinding.ActivitySuperNeqabtyMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SuperNeqabtyMainActivity : AppCompatActivity() {
    private val viewModel: SuperNeqabtyMainViewModel by viewModels()
    lateinit var binding: ActivitySuperNeqabtyMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuperNeqabtyMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getSyndicateAds(1)
        viewModel.ads.observe(this){
          binding.tv.text = it[0].title
        }
    }
}