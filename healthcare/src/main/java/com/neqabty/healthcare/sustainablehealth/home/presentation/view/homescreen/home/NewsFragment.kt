package com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.FragmentNewsBinding
import com.neqabty.healthcare.news.domain.entity.NewsEntity
import com.neqabty.healthcare.news.view.newsdetails.NewsDetailsActivity
import com.neqabty.healthcare.news.view.newslist.NewsAdapter
import com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private val mAdapter = NewsAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getAllNews()
        homeViewModel.news.observe(requireActivity()){

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
                            binding.noNewsLayout.visibility = View.VISIBLE
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(requireActivity(), resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

        binding.newsRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            NewsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: NewsEntity) {
                val intent = Intent(requireActivity(), NewsDetailsActivity::class.java)
                intent.putExtra("id", item.id)
                startActivity(intent)
            }
        }
    }

}