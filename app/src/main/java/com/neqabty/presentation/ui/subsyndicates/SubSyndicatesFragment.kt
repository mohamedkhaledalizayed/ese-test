package com.neqabty.presentation.ui.subsyndicates

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.SubsyndicatesFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.SyndicateUI
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting
import kotlinx.android.synthetic.main.subsyndicates_fragment.*
import javax.inject.Inject

@OpenForTesting
class SubSyndicatesFragment : DialogFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<SubsyndicatesFragmentBinding>()
    private var adapter by autoCleared<SubSyndicatesAdapter>()

    lateinit var subSyndicatesViewModel: SubSyndicatesViewModel

    lateinit var syndicate: SyndicateUI

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.subsyndicates_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subSyndicatesViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SubSyndicatesViewModel::class.java)

        val bundle = this.arguments
        bundle?.let { syndicate = it.getParcelable("syndicate") }

        initializeViews()

        val adapter = com.neqabty.presentation.ui.subsyndicates.SubSyndicatesAdapter(dataBindingComponent, appExecutors) { subSyndicate ->
            PreferencesHelper(requireContext()).mainSyndicate = syndicate.id
            PreferencesHelper(requireContext()).subSyndicate = subSyndicate.id
            targetFragment?.onActivityResult(targetRequestCode, 200, null)
            this@SubSyndicatesFragment.dismiss()
        }
        this.adapter = adapter
        binding.rvSubSyndicates.adapter = adapter
        var mDividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        rvSubSyndicates.addItemDecoration(mDividerItemDecoration)

        subSyndicatesViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
//        subSyndicatesViewModel.errorState.observe(this, Observer { throwable ->
//            throwable?.let {
//                Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_LONG).show()
//            }
//        })
//
//        subSyndicatesViewModel.getSubSyndicates(syndicate.id.toString())
        subSyndicatesViewModel.viewState.value?.subSyndicates = syndicate.subSyndicates
    }

    private fun handleViewState(state: SubSyndicatesViewState) {
//        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.subSyndicates?.let {
            adapter.submitList(it)
        }
    }

    fun initializeViews() {
    }

//region

// endregion
}
