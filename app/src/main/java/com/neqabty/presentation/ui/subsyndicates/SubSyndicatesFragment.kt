package com.neqabty.presentation.ui.subsyndicates

import androidx.lifecycle.Observer
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.SubsyndicatesFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.entities.SyndicateUI
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.subsyndicates_fragment.*
import javax.inject.Inject

@AndroidEntryPoint
class SubSyndicatesFragment : DialogFragment() {
    @Inject
    lateinit var sharedPref: PreferencesHelper

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<SubsyndicatesFragmentBinding>()
    private var adapter by autoCleared<SubSyndicatesAdapter>()

    private val subSyndicatesViewModel: SubSyndicatesViewModel by viewModels()

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

        val bundle = this.arguments
        bundle?.let { syndicate = it.getParcelable("syndicate")!! }

        initializeViews()

        val adapter = com.neqabty.presentation.ui.subsyndicates.SubSyndicatesAdapter(dataBindingComponent, appExecutors) { subSyndicate ->
            sharedPref.mainSyndicate = syndicate.id
            sharedPref.subSyndicate = subSyndicate.id
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
//        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.subSyndicates?.let {
            adapter.submitList(it)
        }
    }

    fun initializeViews() {
    }

//region

// endregion
}
