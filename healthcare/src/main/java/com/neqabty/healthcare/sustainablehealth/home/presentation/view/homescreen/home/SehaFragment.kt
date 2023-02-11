package com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.FragmentSehaBinding
import com.neqabty.healthcare.sustainablehealth.home.presentation.view.about.AboutFragment
import com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.AboutAdapter
import com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SehaFragment : Fragment() {

    @Inject
    lateinit var sharedPreferences: PreferencesHelper
    private val homeViewModel: HomeViewModel by viewModels()
    private val aboutAdapter = AboutSehaAdapter()
    private val packagesAdapter = PackageSehaAdapter()
    private lateinit var binding: FragmentSehaBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSehaBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.aboutRecycler.adapter = aboutAdapter
        binding.packagesRecycler.adapter = packagesAdapter

        binding.aboutRecycler.adapter = aboutAdapter
        aboutAdapter.onItemClickListener = object :
            AboutSehaAdapter.OnItemClickListener {
            override fun setOnItemClickListener(title: String, content: String) {
                aboutDetails(title, content,"")
            }
        }

        homeViewModel.getAboutList()
        homeViewModel.aboutList.observe(requireActivity()){

            it.let { resource ->
                when(resource.status){
                    Status.LOADING ->{
                        binding.progressCircularAbout.visibility = View.VISIBLE
                    }
                    Status.SUCCESS ->{
                        binding.progressCircularAbout.visibility = View.GONE
                        aboutAdapter.submitList(resource.data)
                    }
                    Status.ERROR ->{
                        binding.progressCircularAbout.visibility = View.GONE
                    }
                }
            }
        }

        if (sharedPreferences.code.isNullOrEmpty()){
            homeViewModel.getPackages(Constants.NEQABTY_CODE)
        }else{
            homeViewModel.getPackages(sharedPreferences.code)
        }
        homeViewModel.packages.observe(requireActivity()) {
            it.let { resource ->

                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        packagesAdapter.submitList(resource.data?.toMutableList())
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                    }
                }

            }
        }
    }

    private fun aboutDetails(title: String, content: String, code: String) {
        val fm: FragmentManager = requireActivity().supportFragmentManager
        val dialog = AboutFragment.newInstance(title, content, code)
        dialog.show(fm, "")
        dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth)

    }

}