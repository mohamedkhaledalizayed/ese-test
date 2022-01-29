package com.neqabty.login.modules.home.presentation.view.homescreen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.neqabty.login.R
import com.neqabty.login.modules.home.domain.entity.SyndicateEntity
import com.neqabty.news.modules.home.presentation.view.homescreen.HomeActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val syndicatesViewModel: SyndicatesViewModel by viewModels()
    private var list: MutableList<SyndicateEntity> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val spin: Spinner = findViewById<View>(R.id.all_syndicates) as Spinner
        spin.onItemSelectedListener = this

        syndicatesViewModel.syndicates.observe(this) {
            if (it.isNotEmpty()) {
                list = it.toMutableList()
                list.add(0, SyndicateEntity(name = "اختار النقابة", image = ""))
                spin.visibility = View.VISIBLE
                spin.adapter = CustomAdapter(this, list)
            }
        }
    }

    fun onCountryPickerClick(view: View) {

    }

    fun send(view: View) {
        syndicatesViewModel.getSyndicates()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (position != 0){
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("id", list[position].id)
            startActivity(intent)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}