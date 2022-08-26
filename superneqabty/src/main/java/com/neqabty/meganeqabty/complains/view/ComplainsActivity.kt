package com.neqabty.meganeqabty.complains.view


import android.os.Bundle
import androidx.activity.viewModels
import com.neqabty.core.ui.BaseActivity
import com.neqabty.core.utils.Status
import com.neqabty.meganeqabty.databinding.ActivityComplainsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComplainsActivity : BaseActivity<ActivityComplainsBinding>() {

    override fun getViewBinding() = ActivityComplainsBinding.inflate(layoutInflater)
    private val complainsViewModel: ComplainsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        complainsViewModel.getAllComplains()
        complainsViewModel.complainStatus.observe(this){
            resource ->

            when(resource.status){
                Status.LOADING -> {

                }
                Status.SUCCESS -> {

                }
                Status.ERROR ->{

                }
            }
        }

    }
}