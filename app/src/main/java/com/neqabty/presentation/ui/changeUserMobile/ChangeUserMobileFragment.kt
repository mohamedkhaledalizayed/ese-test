package com.neqabty.presentation.ui.changeUserMobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.ChangeUserMobileFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.change_user_mobile_fragment.*
import kotlinx.android.synthetic.main.change_user_mobile_fragment.ccp
import javax.inject.Inject


class ChangeUserMobileFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<ChangeUserMobileFragmentBinding>()

    lateinit var changeUserMobileViewModel: ChangeUserMobileViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.change_user_mobile_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        changeUserMobileViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ChangeUserMobileViewModel::class.java)

        changeUserMobileViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        changeUserMobileViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                changeUserMobileViewModel.changeUserMobile(PreferencesHelper(requireContext()).user, edNationalNumber.text.toString(), if(ccp.selectedCountryNameCode.equals("EG", true)) ccp.fullNumber.removeRange(0,1) else ccp.fullNumber, PreferencesHelper(requireContext()).mobile)
            }, cancelCallback = {
                llSuperProgressbar.visibility = View.GONE
            }, message = error?.message)
        })

        initializeViews()
    }

    fun initializeViews() {
        binding.edCurrentMobileNumber.setText(PreferencesHelper(requireContext()).mobile)
        binding.ccp.registerCarrierNumberEditText(binding.edNewMobileNumber)

        binding.bSend.setOnClickListener {
            if (binding.edNationalNumber.text.toString().length < 4)
                showAlert(getString(R.string.invalid_national_id))
            else if (!binding.ccp.isValidFullNumber)
                showAlert(getString(R.string.invalid_mobile))
            else
                changeUserMobileViewModel.changeUserMobile(PreferencesHelper(requireContext()).user, edNationalNumber.text.toString(), if(ccp.selectedCountryNameCode.equals("EG", true)) ccp.fullNumber.removeRange(0,1) else ccp.fullNumber, PreferencesHelper(requireContext()).mobile)
        }
    }

    private fun handleViewState(state: ChangeUserMobileViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.changeUserMobileUI?.let {
            PreferencesHelper(requireContext()).clearAll()
            navController().popBackStack()
            navController().navigate(R.id.openLoginFragment)
            Toast.makeText(requireContext(), it.msg, Toast.LENGTH_LONG).show()
        }
    }

//region
// endregion

    fun navController() = findNavController()
}
