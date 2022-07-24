package com.neqabty.login.modules.login.presentation.view.homescreen

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.neqabty.login.R
import com.neqabty.login.core.ui.BaseActivity
import com.neqabty.login.core.utils.ParcelClickListenerExtra
import com.neqabty.login.core.utils.Status
import com.neqabty.login.core.utils.isMobileValid
import com.neqabty.login.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog
import java.util.regex.Pattern

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private lateinit var dialog: AlertDialog
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var toolbar: Toolbar
    private var isHidden = true

    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        setupToolbar(titleResId = R.string.login)

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("من فضلك انتظر...")
            .build()


        binding.etUsername.setText(sharedPreferences.mobile)
        if (sharedPreferences.mobile.isNotEmpty()){
            Log.e("login", "True")
        }
        loginViewModel.user.observe(this)  {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dialog.show()
                    }
                    Status.SUCCESS -> {
                        dialog.dismiss()
                        if (resource.data!!.user.nationalId.isNotEmpty()){
                            sharedPreferences.isPhoneVerified = true
                            sharedPreferences.token = resource.data.token
                            sharedPreferences.mobile = resource.data.user.mobile
                            sharedPreferences.name = resource.data.user.fullName
                            sharedPreferences.nationalId = resource.data.user.nationalId
                            sharedPreferences.userImage = "${resource.data.user.image}"
                            finish()
                        }else{
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                    }
                }
            }

        }
    }

    fun showHidePassword(view: View) {
        if (isHidden) {
            isHidden = false
            binding.etPassword.transformationMethod = null
            binding.showHide.setImageResource(R.drawable.ic_baseline_visibility_24)
        } else {
            isHidden = true
            binding.etPassword.transformationMethod = PasswordTransformationMethod()
            binding.showHide.setImageResource(R.drawable.ic_baseline_visibility_off_24)
        }
    }

    fun login(view: View) {

        if (binding.etUsername.text.toString().isEmpty()){
            Toast.makeText(this, "من فضلك ادخل رقم الموبايل", Toast.LENGTH_LONG).show()
            return
        }

        if (binding.etPassword.text.toString().isEmpty()){
            Toast.makeText(this, "من فضلك ادخل كلمة المرور", Toast.LENGTH_LONG).show()
            return
        }

//        if (binding.etPassword.text.toString().length < 6){
//            Toast.makeText(this, "من فضلك ادخل كلمة مرور قوية", Toast.LENGTH_LONG).show()
//            return
//        }

        if(!binding.etUsername.text.toString().isMobileValid()) {
            Toast.makeText(this, "من فضلك ادخل رقم صحيح", Toast.LENGTH_LONG).show()
            return
        }

        loginViewModel.login(binding.etUsername.text.toString(),
            binding.etPassword.text.toString())
    }
    fun signUp(view: View) {
//        (intent.getSerializableExtra("listener")!! as Runnable).run()
        startActivity(intent.getParcelableExtra<Intent>(Intent.EXTRA_INTENT)?.putExtra("code", intent.getStringExtra("code")))
        finish()
//                val intent = Intent(this@LoginActivity, SignupActivity::class.java)
//                startActivity(intent)
    }
}