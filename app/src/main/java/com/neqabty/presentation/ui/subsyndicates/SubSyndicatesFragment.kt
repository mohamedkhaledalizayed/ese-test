package com.neqabty.presentation.ui.subsyndicates

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.SubsyndicatesFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class SubSyndicatesFragment : DialogFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<SubsyndicatesFragmentBinding>()
    private var adapter by autoCleared<SubSyndicatesAdapter>()

    lateinit var subSyndicatesViewModel: SubSyndicatesViewModel

    var mainSyndicateId: Int = 0

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
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
        bundle?.let { mainSyndicateId = it.getInt("id", -1) }

        initializeViews()


        val adapter = SubSyndicatesAdapter(dataBindingComponent, appExecutors) { subSyndicate ->
            PreferencesHelper(requireContext()).mainSyndicate = mainSyndicateId.toString()
            PreferencesHelper(requireContext()).subSyndicate = subSyndicate.id.toString()
            targetFragment?.onActivityResult(targetRequestCode, 200,null)
            this@SubSyndicatesFragment.dismiss()
        }
        this.adapter = adapter
        binding.rvSubSyndicates.adapter = adapter

        subSyndicatesViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        subSyndicatesViewModel.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_LONG).show()
            }
        })

        subSyndicatesViewModel.getSubSyndicates(mainSyndicateId.toString())
    }

    private fun handleViewState(state: SubSyndicatesViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.subSyndicates?.let {
            adapter.submitList(it)
        }
    }

    fun initializeViews() {
    }


//region


// endregion
}
