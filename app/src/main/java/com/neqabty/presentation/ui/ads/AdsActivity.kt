 package com.neqabty.presentation.ui.ads

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.presentation.entities.AdUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.ads_activity.*
import javax.inject.Inject

 @AndroidEntryPoint
 class AdsActivity : AppCompatActivity() {

    private val adsViewModel: AdsViewModel by viewModels()

    @Inject
    lateinit var appExecutors: AppExecutors

    lateinit var adsList: ArrayList<AdUI>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ads_activity)

        adsList = intent.getParcelableArrayListExtra<AdUI>("adsList") as ArrayList<AdUI>

        initializeViews()

//        adsViewModel.viewState.observe(this, Observer {
//            if (it != null) handleViewState(it)
//        })
//        adsViewModel.errorState.observe(this, Observer { error ->
//            finish()
//        })
//        adsViewModel.getAds(sectionId)
    }

    fun initializeViews() {
        Glide.with(this).load(adsList[0].imgURL).listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, isFirstResource: Boolean): Boolean {
                initializeViews()
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                clHolder.visibility = View.VISIBLE
                return false
            }
        }).into(ivImg)
        ivClose.setOnClickListener {
            finish()
        }
    }

// endregion
}
