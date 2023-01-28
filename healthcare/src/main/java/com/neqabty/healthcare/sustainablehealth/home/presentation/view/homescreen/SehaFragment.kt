package com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.FragmentSehaBinding

class SehaFragment : Fragment() {

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
    }

}