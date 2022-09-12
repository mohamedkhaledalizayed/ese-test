package com.neqabty.healthcare.modules.syndicates.presentation.view.homescreen

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.core.data.Constants.ESE_CODE
import com.neqabty.core.data.Constants.NEQABTY_CODE
import com.neqabty.core.data.Constants.isSyndicateMember
import com.neqabty.core.data.Constants.selectedSyndicateCode
import com.neqabty.core.data.Constants.selectedSyndicatePosition
import com.neqabty.core.ui.BaseActivity
import com.neqabty.core.utils.Status
import com.neqabty.healthcare.databinding.ActivitySyndicateBinding
import com.neqabty.healthcare.modules.syndicates.domain.entity.SyndicateEntity
import com.neqabty.meganeqabty.R
import com.neqabty.meganeqabty.home.view.homescreen.HomeActivity
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.effet.RippleEffect
import com.takusemba.spotlight.shape.RoundedRectangle
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
//                            startSpotlight()
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

                        val intent = Intent(this@SyndicateActivity, com.neqabty.healthcare.modules.home.presentation.view.homescreen.HomeActivity::class.java)
                        intent.putExtra("id", item.id)
                        sharedPreferences.mainSyndicate = item.id
                        sharedPreferences.code = item.code
                        sharedPreferences.image = item.image
                        sharedPreferences.syndicateName = item.name
                        startActivity(intent)
                        finish()
                    }else{
                        isSyndicateMember = true
                        selectedSyndicateCode = item.code
                        selectedSyndicatePosition = position + 1

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


    @SuppressLint("ResourceAsColor")
    private fun startSpotlight() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val width: Int = displayMetrics.widthPixels
        val height: Int = displayMetrics.heightPixels

        var x = 0f
        var y = 0f
        if (sharedPreferences.language == "en"){

            x = width - 220f
            y = (height / 10) * 3f + 500
        }else{

            x = 220f
            y = (height / 10) * 3f + 500

        }
        val firstRoot = LinearLayout(this)
        val first = layoutInflater.inflate(R.layout.feature_layout_item, firstRoot)
        val firstTarget = com.takusemba.spotlight.Target.Builder()
            .setAnchor(x, y)
            .setEffect(RippleEffect(100f, 300f, R.color.white))
            .setShape(RoundedRectangle(500f,360f,50f))
            .setOverlay(first)
            .build()

        val spotlight = Spotlight.Builder(this)
            .setTargets(firstTarget)
            .setBackgroundColorRes(R.color.spotlightBackground)
            .setDuration(1000L)
            .setAnimation(DecelerateInterpolator(2f))
            .build()

        spotlight.start()

        val closeSpotlight = View.OnClickListener { spotlight.finish() }

        first.findViewById<View>(R.id.close_spotlight).setOnClickListener(closeSpotlight)

    }


    override fun onBackPressed() {
        showAlertDialog(getString(R.string.close_app))
    }

    private fun showAlertDialog(message: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle(getString(R.string.alert))
        alertDialog.setMessage(message)
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, getString(R.string.agree)
        ) { dialog, _ ->
            dialog.dismiss()
            finishAffinity()
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, getString(R.string.no_btn)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()

    }

}