package com.neqabty.signup.modules.home.presentation.view.homescreen

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.neqabty.signup.R
import com.neqabty.signup.core.utils.Status
import com.neqabty.signup.modules.home.domain.entity.SignupParams
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    private val signupViewModel: SignupViewModel by viewModels()
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        signupViewModel.user.observe(this){

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

    fun signUp(view: View) {
        signupViewModel.signup(
            SignupParams(
                entityCode = "e0005",
                licenceNumber = "2293",
                membershipId = "2731",
                mobile = "01113595435",
                nationalId = "26910160200213",
                password = "123456"
            )
        )
    }
}