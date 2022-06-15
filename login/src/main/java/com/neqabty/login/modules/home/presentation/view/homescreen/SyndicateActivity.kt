package com.neqabty.login.modules.home.presentation.view.homescreen

import android.content.Intent
import android.os.Bundle
import android.widget.GridView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.neqabty.login.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SyndicateActivity : AppCompatActivity() {
    private val syndicatesViewModel: SyndicatesViewModel by viewModels()
    private val mainAdapter = SyndicateAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_syndicate)


        syndicatesViewModel.getSyndicates()
        syndicatesViewModel.syndicates.observe(this) {
            findViewById<GridView>(R.id.gridView).adapter = mainAdapter
            mainAdapter.submitList(it)

        }

    }
}