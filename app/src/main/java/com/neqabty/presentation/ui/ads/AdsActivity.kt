 package com.neqabty.presentation.ui.ads

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.presentation.entities.AdUI
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.ads_activity.*
import javax.inject.Inject

 class AdsActivity : AppCompatActivity(), HasAndroidInjector {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    lateinit var adsViewModel: AdsViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    var sectionId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ads_activity)

        sectionId = intent.getIntExtra("sectionID" , 0)

        adsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(AdsViewModel::class.java)

        adsViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        adsViewModel.errorState.observe(this, Observer { error ->
        })
        adsViewModel.getAds(sectionId)
    }

    private fun handleViewState(state: AdsViewState) {
        state.ad?.let {
            initializeViews(it)
        }
    }

    fun initializeViews(ad: AdUI) {
        Glide.with(this).load(ad.imgURL).into(ivImg)
        ivClose.setOnClickListener {
            finish()
        }
    }

    //region
    override fun androidInjector(): AndroidInjector<Any> = androidInjector

// endregion
}
