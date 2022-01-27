package com.neqabty.login.modules.home.presentation.view.homescreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.login.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SyndicateActivity : AppCompatActivity() {
    private val syndicatesViewModel: SyndicatesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        syndicatesViewModel.getSyndicates()
        syndicatesViewModel.syndicates.observe(this){
            Toast.makeText(applicationContext,it[0].name,Toast.LENGTH_SHORT).show()
        }
    }
}