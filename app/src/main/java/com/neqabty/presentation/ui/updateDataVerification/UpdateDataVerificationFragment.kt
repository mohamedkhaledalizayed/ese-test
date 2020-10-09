package com.neqabty.presentation.ui.updateDataVerification

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.UpdateDataVerificationFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.update_data_verification_fragment.*
import javax.inject.Inject

class UpdateDataVerificationFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<UpdateDataVerificationFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors
    lateinit var verificationCode: String

    lateinit var updateDataVerificationViewModel: UpdateDataVerificationViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.update_data_verification_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateDataVerificationViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(UpdateDataVerificationViewModel::class.java)

        updateDataVerificationViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        updateDataVerificationViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                updateDataVerificationViewModel.verifyUser(PreferencesHelper(requireContext()).user, PreferencesHelper(requireContext()).mobile)
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })
        updateDataVerificationViewModel.verifyUser(PreferencesHelper(requireContext()).user, PreferencesHelper(requireContext()).mobile)
    }

    private fun handleViewState(state: UpdateDataVerificationViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (!state.isLoading && state.code.isNotEmpty()) {
            verificationCode = state.code
            initializeViews()
        }
    }

    fun initializeViews() {
        svContent.visibility = View.VISIBLE
        bSend.setOnClickListener {
            if (binding.edVerificationNumber.text.toString() == verificationCode)
                navController().navigate(UpdateDataVerificationFragmentDirections.updateDataDetails())
            else
                showErrorAlert(getString(R.string.wrong_verification_code))
        }
    }

    fun showErrorAlert(message: String) {
        builder = AlertDialog.Builder(requireContext())
        builder?.setTitle(getString(R.string.thanks))
        builder?.setMessage(message)
        builder?.setCancelable(true)
        builder?.setPositiveButton(getString(R.string.ok_btn)) { dialog, which ->
            dialog.dismiss()
        }
        var dialog = builder?.create()
        dialog?.show()
    }

// endregion

    fun navController() = findNavController()
}
