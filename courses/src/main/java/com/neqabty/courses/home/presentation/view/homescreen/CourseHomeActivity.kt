package com.neqabty.courses.home.presentation.view.homescreen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.example.courses.databinding.ActivityMainBinding
import com.neqabty.courses.core.ui.BaseActivity
import com.neqabty.courses.core.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CourseHomeActivity : BaseActivity<ActivityMainBinding>() {
    private val homeViewModel: HomeViewModel by viewModels()
    private val mAdapter = CoursesAdapter()
    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(title = "الدورات التدريبية")

        binding.coursesRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            CoursesAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: String) {

            }
        }

        homeViewModel.getCourses()

        homeViewModel.courses.observe(this){
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data!!.isNotEmpty()){
                            mAdapter.submitList(resource.data)
                        }else{
                            binding.noCoursesLayout.visibility = View.VISIBLE
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                    }
                }
            }
        }


    }


}