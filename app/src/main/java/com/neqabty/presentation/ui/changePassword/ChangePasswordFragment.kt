package com.neqabty.presentation.ui.changePassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.ChangePasswordFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.change_password_fragment.*
import javax.inject.Inject

class ChangePasswordFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<ChangePasswordFragmentBinding>()

    lateinit var changePasswordViewModel: ChangePasswordViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.change_password_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        changePasswordViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ChangePasswordViewModel::class.java)

        changePasswordViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        changePasswordViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                changePassword()
            }, cancelCallback = {
                llSuperProgressbar.visibility = View.GONE
            }, message = error?.message)
        })
        initializeViews()
    }

    fun initializeViews() {
        binding.bSend.setOnClickListener {
            if (binding.edCurrentPassword.text.toString().length != 4 || binding.edNewPassword.text.toString().length != 4 || binding.edConfirmNewPassword.text.toString().length != 4)
                showAlert(getString(R.string.invalid_data))
            else if (!binding.edNewPassword.text.toString().equals(binding.edConfirmNewPassword.text.toString()))
                showAlert(getString(R.string.unmatched_password))
            else
                changePassword()
        }
    }

    private fun handleViewState(state: ChangePasswordViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (state.isSuccessful) {
            showAlert(state.msg){
                navController().navigateUp()
            }
        }
    }

    fun changePassword() {
        changePasswordViewModel.changePassword(PreferencesHelper(requireContext()).mobile, binding.edCurrentPassword.text.toString(), edNewPassword.text.toString())
    }

    //region
// endregion

    fun navController() = findNavController()
}
