package com.neqabty.healthcare.chefaa.verifyuser.view



import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.Constants.mobileNumber
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityVerifyUserBinding
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog

@AndroidEntryPoint
class VerifyUserActivity : BaseActivity<ActivityVerifyUserBinding>() {
    private lateinit var loading: AlertDialog
    private val verifyUserViewModel: VerifyUserViewModel by viewModels()
    override fun getViewBinding() = ActivityVerifyUserBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loading = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(getString(R.string.please_wait))
            .build()

        binding.btnConfirm.setOnClickListener {
            if (binding.code.text.toString().isNullOrEmpty()){
                Toast.makeText(this@VerifyUserActivity, "من فضلك ادخل الكود.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            verifyUserViewModel.verifyUser(mobile = mobileNumber, code = binding.code.text.toString())
        }

        verifyUserViewModel.status.observe(this){
            it.let { resource ->
                when(resource.status){
                    Status.LOADING ->{
                        loading.show()
                    }
                    Status.SUCCESS ->{
                        loading.dismiss()
                        if (resource.data!!){
                            Toast.makeText(this@VerifyUserActivity, "تم تاكيد رقم الهاتف بنجاح.", Toast.LENGTH_LONG).show()
                            finish()
                        }else{
                            Toast.makeText(this@VerifyUserActivity, "خطا, من فضلك ادخل الكود الصحيح.", Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR ->{
                        loading.dismiss()
                        Toast.makeText(this@VerifyUserActivity, "حدث خطا.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

}