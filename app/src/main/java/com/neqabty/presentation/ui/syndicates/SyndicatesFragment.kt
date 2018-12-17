package com.neqabty.presentation.ui.syndicates

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.SyndicatesFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
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
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setupToolbar(true)
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


        val adapter = SyndicatesAdapter(dataBindingComponent, appExecutors) { syndicate ->
//            navController().navigate(
////                    SyndicatesFragmentDirections.showImage(history.path!!)
//            )
        }
        this.adapter = adapter
        binding.rvSyndicates.adapter = adapter

        syndicatesViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        syndicatesViewModel.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_LONG).show()
            }
        })

        syndicatesViewModel.getSyndicates()
    }

    private fun handleViewState(state: SyndicatesViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.syndicates?.let {
            adapter.submitList(it)
        }
    }

    fun initializeViews() {
    }


//region


// endregion

    fun navController() = findNavController()
}
