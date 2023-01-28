package com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.neqabty.healthcare.databinding.FragmentSyndicatesBinding


class SyndicatesFragment : Fragment() {

    private var syndicatesAdapter: SyndicatesAdapter = SyndicatesAdapter()
    private lateinit var binding: FragmentSyndicatesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSyndicatesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.syndicatesRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.syndicatesRecycler.adapter = syndicatesAdapter

        syndicatesAdapter.onItemClickListener = object :
            SyndicatesAdapter.OnItemClickListener {
            override fun setOnItemClickListener(title: String, content: String) {

            }

        }
    }

}