package com.neqabty.healthcare.commen.syndicates.presentation.view.homescreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.core.data.Constants.ESE_CODE
import com.neqabty.healthcare.core.data.Constants.isSyndicateMember
import com.neqabty.healthcare.core.data.Constants.selectedSyndicateCode
import com.neqabty.healthcare.core.data.Constants.selectedSyndicatePosition
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.eselanding.EseLandingActivity
import com.neqabty.healthcare.databinding.ActivitySyndicateBinding
import com.neqabty.healthcare.mega.home.view.MegaHomeActivity
import com.neqabty.healthcare.commen.syndicates.domain.entity.SyndicateEntity
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
                            mainAdapter.submitList(resource.data.toMutableList()
                                .also { list -> list.add(0, SyndicateEntity("e03", image = "", name = "نقابة المهندسين")) })
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
                if (item.code == ESE_CODE) {
                    val intent = Intent(this@SyndicateActivity, EseLandingActivity::class.java)
                    startActivity(intent)
                } else {
                    isSyndicateMember = true
                    selectedSyndicateCode = item.code
                    selectedSyndicatePosition = position + 1

                    val intent = Intent(this@SyndicateActivity, MegaHomeActivity::class.java)
                    if (sharedPreferences.isAuthenticated && sharedPreferences.code == item.code){

                    }else if (sharedPreferences.isAuthenticated && sharedPreferences.code != item.code){
                        intent.putExtra("isGuest", true)
                        intent.putExtra("code", item.code)
                        intent.putExtra("image", item.image)
                        intent.putExtra("syndicateName", item.name)
                    }else{
                        sharedPreferences.mainSyndicate = item.id
                        sharedPreferences.code = item.code
                        sharedPreferences.image = item.image
                        sharedPreferences.syndicateName = item.name
                    }
                    startActivity(intent)
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

}