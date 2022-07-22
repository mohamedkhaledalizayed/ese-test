package com.neqabty.courses.offers.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.courses.R
import com.neqabty.courses.offers.presentation.viewmodel.OffersViewModel
import dagger.hilt.android.AndroidEntryPoint

const val COURSEID = "COURSEID"

@AndroidEntryPoint
class OffersActivity : AppCompatActivity() {
    private val viewModel: OffersViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offers)
        val courseId = intent.getIntExtra(COURSEID, -1)
        viewModel.getCoursesOffers(courseId)
        viewModel.offers.observe(this){
            Toast.makeText(this,it.size.toString(),Toast.LENGTH_SHORT).show()
        }
    }
}