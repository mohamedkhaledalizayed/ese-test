package com.neqabty.superneqabty.syndicates.presentation.view.homescreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.neqabty.superneqabty.R
import com.neqabty.superneqabty.core.utils.Status
import com.neqabty.superneqabty.home.view.homescreen.HomeActivity
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

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        findViewById<ProgressBar>(R.id.progress_circular).visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        findViewById<ProgressBar>(R.id.progress_circular).visibility = View.GONE
                        if (resource.data!!.isNotEmpty()){
                            findViewById<GridView>(R.id.gridView).adapter = mainAdapter
                            mainAdapter.submitList(resource.data)
                        }else{
                            Toast.makeText(this, "Empty", Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        findViewById<ProgressBar>(R.id.progress_circular).visibility = View.GONE
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

        mainAdapter.onItemClickListener = object :
            SyndicateAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: Int) {
                val intent = Intent(this@SyndicateActivity, HomeActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
                finishAffinity()
            }
        }
    }
}