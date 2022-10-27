package com.neqabty.courses.offers.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.courses.databinding.ActivityOffersBinding
import com.neqabty.courses.core.data.Constants.COURSEID
import com.neqabty.courses.core.data.Constants.OFFERDETAILS
import com.neqabty.courses.core.ui.BaseActivity
import com.neqabty.courses.core.utils.Status
import com.neqabty.courses.offers.presentation.OffersAdapter
import com.neqabty.courses.offers.presentation.viewmodel.OffersViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class OffersActivity : BaseActivity<ActivityOffersBinding>() {

    override fun getViewBinding() = ActivityOffersBinding.inflate(layoutInflater)
    private val viewModel: OffersViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(title = "العروض")
        val courseId = intent.getIntExtra(COURSEID, -1)
        viewModel.getCoursesOffers(courseId)

        val adapter = OffersAdapter(){
            startActivity(Intent(this,OfferDetailsActivity::class.java).putExtra(OFFERDETAILS,it))
        }

        binding.offersRv.adapter = adapter
        viewModel.offers.observe(this){
            it.let {
                resource ->
                when(resource.status){
                    Status.LOADING->{
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS->{
                        binding.progressCircular.visibility = View.GONE
                        adapter.submitList(it.data!!)
                    }
                    Status.ERROR->{
                        binding.progressCircular.visibility = View.GONE
                    }
                }
            }
        }
    }
}