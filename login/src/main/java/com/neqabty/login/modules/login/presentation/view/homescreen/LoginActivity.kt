package com.neqabty.login.modules.login.presentation.view.homescreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.neqabty.login.R
import com.neqabty.login.core.utils.Status
import com.neqabty.news.modules.home.presentation.view.homescreen.HomeActivity
import com.neqabty.signup.modules.home.presentation.view.homescreen.SignupActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        loginViewModel.user.observe(this)  {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                    }
                    Status.SUCCESS -> {
                        if (resource.data!!.nationalId.isNotEmpty()){

                        }else{
                            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        Log.e("ik", resource.message.toString())
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }

    fun showHidePassword(view: View) {}
    fun login(view: View) {
        loginViewModel.login(findViewById<EditText>(R.id.et_username).text.toString(),
            findViewById<EditText>(R.id.et_password).text.toString())
    }
    fun signUp(view: View) {
        startActivity(Intent(this, SignupActivity::class.java))
    }
}