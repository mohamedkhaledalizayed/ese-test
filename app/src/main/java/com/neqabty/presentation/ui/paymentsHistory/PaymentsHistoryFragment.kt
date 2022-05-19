package com.neqabty.presentation.ui.paymentsHistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.PaymentsHistoryFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PaymentsHistoryFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<PaymentsHistoryFragmentBinding>()
    private var adapter by autoCleared<PaymentsAdapter>()

    private val paymentsHistoryViewModel: PaymentsHistoryViewModel by viewModels()

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.payments_history_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initializeViews()

        val adapter = PaymentsAdapter(dataBindingComponent, appExecutors) {}
        this.adapter = adapter
        binding.rvPayments.adapter = adapter

        paymentsHistoryViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        paymentsHistoryViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                paymentsHistoryViewModel.getHistory(sharedPref.user)
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })

        paymentsHistoryViewModel.getHistory(sharedPref.user)
    }

    private fun handleViewState(state: PaymentsHistoryViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.payments?.let {
            adapter.submitList(it)
            return@let
        }
    }

    fun initializeViews() {
    }

//region

// endregion

    fun navController() = findNavController()
}
