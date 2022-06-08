package com.neqabty.healthcare.modules.home.presentation.view.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.neqabty.healthcare.databinding.FragmentAboutBinding



class AboutFragment : DialogFragment() {


    private lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAboutBinding.inflate(layoutInflater)

        binding.close.setOnClickListener { dismiss() }
        return binding.root
    }

}