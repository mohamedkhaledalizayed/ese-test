package com.neqabty.courses.offers.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.courses.R
import com.example.courses.databinding.ActivityOffersBinding
import com.neqabty.courses.offers.presentation.OffersAdapter
import com.neqabty.courses.offers.presentation.viewmodel.OffersViewModel
import dagger.hilt.android.AndroidEntryPoint

const val COURSEID = "COURSEID"

@AndroidEntryPoint
class OffersActivity : AppCompatActivity() {
    private val viewModel: OffersViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityOffersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val courseId = intent.getIntExtra(COURSEID, -1)
        viewModel.getCoursesOffers(courseId)
        val adapter = OffersAdapter()
        binding.offersRv.adapter = adapter
        viewModel.offers.observe(this){
            adapter.submitList(it)
        }
    }
}