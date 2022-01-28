package com.neqabty.login.modules.home.presentation.view.homescreen

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.neqabty.login.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val syndicatesViewModel: SyndicatesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val spin: Spinner = findViewById<View>(R.id.all_syndicates) as Spinner
        spin.onItemSelectedListener = this

        syndicatesViewModel.syndicates.observe(this){
            Toast.makeText(applicationContext,it[0].name,Toast.LENGTH_SHORT).show()
            if (it.isNotEmpty()){
                spin.visibility = View.VISIBLE
                spin.adapter = CustomAdapter(this, it)
            }
        }
    }

    fun onCountryPickerClick(view: View) {}
    fun send(view: View) {syndicatesViewModel.getSyndicates()}
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}