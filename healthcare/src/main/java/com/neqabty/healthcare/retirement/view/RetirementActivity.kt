package com.neqabty.healthcare.retirement.view


import android.os.Bundle
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityRetirementBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RetirementActivity : BaseActivity<ActivityRetirementBinding>() {

    private val viewModel: RetirementViewModel by viewModels()
    override fun getViewBinding() = ActivityRetirementBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retirement)

        viewModel.checkValidation("50780304", "26302252701111")

        viewModel.getPensionInfo(intent.getStringExtra("member") ?: "")
        viewModel.pensionInfo.observe(this){
            when(it.status){
                Status.LOADING ->{
                }
                Status.SUCCESS ->{

                }
                Status.ERROR ->{
//                    binding.progressCircular.visibility = View.GONE
//                    if (it.message.toString().split("#")[0].trim() == "3ak") {
//                        val error = Gson().fromJson(
//                            it.message.toString().split("#")[1].trim(),
//                            ErrorBody::class.java
//                        )
//                        binding.syndicateid.text = error.error
//
//                    }else{
//                        binding.syndicateid.text = "حدث خطاء"
//                    }
                }
            }
        }

    }
}