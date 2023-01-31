package com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.syndicates.domain.entity.SyndicateEntity
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.FragmentSyndicatesBinding
import com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SyndicatesFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
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

        homeViewModel.getSyndicates()
        homeViewModel.syndicates.observe(requireActivity()) {

            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        if (resource.data!!.isNotEmpty()){
                            binding.syndicatesRecycler.adapter = syndicatesAdapter
                            syndicatesAdapter.submitList(resource.data.toMutableList()
                                .also { list -> list.add(0, SyndicateEntity("e03", image = "", name = "نقابة المهندسين")) })
                        }else{
                            Toast.makeText(requireActivity(), getString(R.string.no_syndicates), Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(requireActivity(), resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

        syndicatesAdapter.onItemClickListener = object :
            SyndicatesAdapter.OnItemClickListener {
            override fun setOnItemClickListener(title: String, content: String) {

            }

        }
    }

}