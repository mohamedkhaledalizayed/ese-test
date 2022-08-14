package com.neqabty.meganeqabty.syndicates.presentation.view.homescreen

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.core.data.Constants
import com.neqabty.core.data.Constants.ESE_CODE
import com.neqabty.core.data.Constants.NEQABTY_CODE
import com.neqabty.core.data.Constants.isSyndicateMember
import com.neqabty.core.data.Constants.selectedSyndicateCode
import com.neqabty.core.data.Constants.selectedSyndicatePosition
import com.neqabty.core.ui.BaseActivity
import com.neqabty.core.utils.Status
import com.neqabty.meganeqabty.R
import com.neqabty.meganeqabty.databinding.ActivitySyndicateBinding
import com.neqabty.meganeqabty.home.view.homescreen.HomeActivity
import com.neqabty.meganeqabty.syndicates.domain.entity.SyndicateEntity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SyndicateActivity : BaseActivity<ActivitySyndicateBinding>() {
    private val syndicatesViewModel: SyndicatesViewModel by viewModels()
    private val mainAdapter = SyndicateAdapter()

    override fun getViewBinding() = ActivitySyndicateBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        syndicatesViewModel.getSyndicates()
        syndicatesViewModel.syndicates.observe(this) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data!!.isNotEmpty()){
                            binding.gridView.adapter = mainAdapter
                            mainAdapter.submitList(resource.data)
                        }else{
                            Toast.makeText(this, getString(R.string.no_syndicates), Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

        mainAdapter.onItemClickListener = object :
            SyndicateAdapter.OnItemClickListener {
            override fun setOnItemClickListener(position: Int, item: SyndicateEntity) {
                if (item.code == ESE_CODE){
                    val launchIntent = packageManager.getLaunchIntentForPackage("com.neqabty")
                    if (launchIntent == null){
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=com.neqabty")
                            )
                        )
                    }else{
                        startActivity(launchIntent)
                    }
                }else{
                    if (item.code == NEQABTY_CODE){
                        isSyndicateMember = false
                        selectedSyndicateCode = ""
                        selectedSyndicatePosition = 0
                    }else{
                        isSyndicateMember = true
                        selectedSyndicateCode = item.code
                        selectedSyndicatePosition = position + 1
                    }

                    val intent = Intent(this@SyndicateActivity, HomeActivity::class.java)
                    intent.putExtra("id", item.id)
                    sharedPreferences.mainSyndicate = item.id
                    sharedPreferences.code = item.code
                    sharedPreferences.image = item.image
                    sharedPreferences.syndicateName = item.name
                    startActivity(intent)
                    finish()
                }

            }
        }
    }
}