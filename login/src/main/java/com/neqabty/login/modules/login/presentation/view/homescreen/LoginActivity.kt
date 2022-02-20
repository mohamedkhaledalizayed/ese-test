package com.neqabty.login.modules.login.presentation.view.homescreen

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import com.neqabty.login.R
import com.neqabty.login.core.utils.Status
import com.neqabty.login.databinding.ActivityLoginBinding
import com.neqabty.news.modules.home.presentation.view.homescreen.HomeActivity
import com.neqabty.signup.core.data.Constants
import com.neqabty.signup.databinding.ActivitySignupBinding
import com.neqabty.signup.modules.home.presentation.view.homescreen.SignupActivity
import dagger.hilt.android.AndroidEntryPoint
import dmax.dialog.SpotsDialog
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var dialog: AlertDialog
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var toolbar: Toolbar
    private var isHidden = true
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("من فضلك انتظر...")
            .build()

        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        if (sharedPreferences.getBoolean(Constants.USERSTATUS, false)){
            Toast.makeText(this, "Login", Toast.LENGTH_LONG).show()
        }
        loginViewModel.user.observe(this)  {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        dialog.show()
                    }
                    Status.SUCCESS -> {
                        dialog.dismiss()
                        if (resource.data!!.nationalId.isNotEmpty()){
                            sharedPreferences.edit().putBoolean(Constants.USERSTATUS, true).commit()
                        }else{
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        dialog.dismiss()
                        Log.e("ik", resource.message.toString())
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
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

        loginViewModel.login(binding.etUsername.text.toString(),
            binding.etPassword.text.toString())
    }
    fun signUp(view: View) {
        startActivity(Intent(this, SignupActivity::class.java))
    }
}