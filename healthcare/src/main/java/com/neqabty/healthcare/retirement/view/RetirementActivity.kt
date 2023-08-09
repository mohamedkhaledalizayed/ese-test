package com.neqabty.healthcare.retirement.view


import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.gson.Gson
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.ErrorBody
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityRetirementBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RetirementActivity : BaseActivity<ActivityRetirementBinding>() {

    private val viewModel: RetirementViewModel by viewModels()
    override fun getViewBinding() = ActivityRetirementBinding.inflate(layoutInflater)

    private var userNumber = ""
    private var nationalId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backBtnHeader.setOnClickListener { finish() }
        binding.backBtn.setOnClickListener { finish() }
        userNumber = intent.getStringExtra("userNumber") ?: ""
        nationalId = intent.getStringExtra("nationalId") ?: ""
        viewModel.checkValidation(userNumber, nationalId)
        viewModel.userStatus.observe(this){
            when(it.status){
                Status.LOADING ->{
                    showProgressDialog()
                }
                Status.SUCCESS ->{
                    hideProgressDialog()
                    if (it.data!!.valid){
                        if (intent.getStringExtra("service")!!  == Constants.VISA){
                            viewModel.getInheritor(userNumber)
                        }else{
                            viewModel.getPensionInfo(userNumber)
                        }
                    }else{
                        Toast.makeText(this@RetirementActivity, "هذا الحساب ليس لديه اي بيانات لدي النقابة", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
                Status.ERROR ->{
                    hideProgressDialog()
                    Toast.makeText(this@RetirementActivity, "${it.message}", Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.pensionInfo.observe(this){
            when(it.status){
                Status.LOADING ->{
                    showProgressDialog()
                }
                Status.SUCCESS ->{
                    hideProgressDialog()
                    binding.total.text = "${it.data?.exchanges?.last()?.mony} جنيه"
                    binding.totalAmount.text = "${it.data?.exchanges?.last()?.mony} جنيه"
                    binding.date.text = "${it.data?.exchanges?.last()?.sendbankdate}"
                    binding.name.text = it.data?.name
                }
                Status.ERROR ->{
                    hideProgressDialog()
                    if (it.message.toString().split("#")[0].trim() == "3ak") {
                        val error = Gson().fromJson(
                            it.message.toString().split("#")[1].trim(),
                            ErrorBody::class.java
                        )
                        Toast.makeText(this@RetirementActivity, "${error.error}", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@RetirementActivity, "${it.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        viewModel.inheritorInfo.observe(this){
            when(it.status){
                Status.LOADING ->{
                    showProgressDialog()
                }
                Status.SUCCESS ->{
                    hideProgressDialog()

                }
                Status.ERROR ->{
                    hideProgressDialog()
                    if (it.message.toString().split("#")[0].trim() == "3ak") {
                        val error = Gson().fromJson(
                            it.message.toString().split("#")[1].trim(),
                            ErrorBody::class.java
                        )
                        Toast.makeText(this@RetirementActivity, "${error.error}", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this@RetirementActivity, "${it.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }
}