package com.neqabty.presentation.ui.syndicates

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.SyndicatesFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.SyndicateUI
import com.neqabty.presentation.ui.subsyndicates.SubSyndicatesFragment
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared

import javax.inject.Inject

class SyndicatesFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<SyndicatesFragmentBinding>()
    private var adapter by autoCleared<SyndicatesAdapter>()

    lateinit var syndicatesViewModel: SyndicatesViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupToolbar(false)
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.syndicates_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        syndicatesViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SyndicatesViewModel::class.java)

        initializeViews()

        val adapter = com.neqabty.presentation.ui.syndicates.SyndicatesAdapter(dataBindingComponent, appExecutors) { syndicate ->
//            if (syndicate.subSyndicates?.size == 0) {
                sharedPref.mainSyndicate = syndicate.id
                sharedPref.subSyndicate = 0
                navController().navigate(
                        SyndicatesFragmentDirections.openHome()
                )
//            } else
//                pickSubSyndicate(syndicate)
        }
        this.adapter = adapter
        binding.rvSyndicates.adapter = adapter

        syndicatesViewModel.viewState.observe(this.requireActivity(), Observer {
            if (it != null) handleViewState(it)
        })
        syndicatesViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                syndicatesViewModel.getSyndicates()
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })

        syndicatesViewModel.getSyndicates()
    }

    private fun handleViewState(state: SyndicatesViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.syndicates?.let {
            val temp = listOf(it[0])
            adapter.submitList(temp)
        }
    }

    fun initializeViews() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 255 && resultCode == 200) {
            navController().navigate(
                    SyndicatesFragmentDirections.openHome()
            )
        }
    }

//region

fun pickSubSyndicate(syndicate: SyndicateUI) {
    val fragmentManager = this@SyndicatesFragment.fragmentManager
    val subSyndicatesFragment = SubSyndicatesFragment()
    val bundle = Bundle()
    bundle.putParcelable("syndicate", syndicate)
    subSyndicatesFragment.arguments = bundle
    subSyndicatesFragment.setTargetFragment(this, 255)
    subSyndicatesFragment.show(requireFragmentManager(), "name")
}
// endregion

    fun navController() = findNavController()
}
