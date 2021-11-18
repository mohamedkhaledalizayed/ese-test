package com.neqabty.presentation.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.LoginFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.util.HasHomeOptionsMenu
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.*

@AndroidEntryPoint
class LoginFragment : BaseFragment(), HasHomeOptionsMenu {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<LoginFragmentBinding>()

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setupToolbar(false)
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.login_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle("")

        initializeObservers()
        initializeViews()
    }

    private fun initializeObservers() {
        loginViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        loginViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                login()
            }, cancelCallback = {
            llSuperProgressbar.visibility = View.GONE
            }, message = error?.message)
        })
    }

    private fun handleViewState(state: LoginViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.user?.let {
            //TODO subscribe
            //TODO set coming data it.details!![0]
//            it.type = "verified"
            if (it.type.equals("verified")) {
                state.user = null
                navController().navigate(LoginFragmentDirections.openLoginWithPasswordFragment(if(ccp.selectedCountryNameCode.equals("EG", true)) ccp.fullNumber.removeRange(0,1) else ccp.fullNumberWithPlus))
            } else if(it.type.equals("visitor")){// visitor
                sharedPref.mobile = it.mobile
                sharedPref.userType = it.type
                sharedPref.jwt = it.jwt
                sharedPref.mainSyndicate = 5
                sharedPref.subSyndicate = 0
                sharedPref.isForceLogout = false
                state.user = null
                navController().navigate(LoginFragmentDirections.openHome())
            }
        }
    }

    fun initializeViews() {
        binding.bSend.setOnClickListener {
            login()
        }
        binding.ccp.registerCarrierNumberEditText(binding.edMobile)
    }

    fun login() {
        if (binding.ccp.isValidFullNumber) {
            loginViewModel.login(if(ccp.selectedCountryNameCode.equals("EG", true)) ccp.fullNumber.removeRange(0,1) else ccp.fullNumberWithPlus)
        }else{
            showAlert(getString(R.string.invalid_mobile))
        }
    }
//region

//    private fun isDataValid(mobile: String): Boolean {
//        return if (mobile.matches(Regex("[0-9]*")) && mobile.trim().length == 11 && (mobile.substring(0, 3).equals("012") || mobile.substring(0, 3).equals("010") || mobile.substring(0, 3).equals("011") || mobile.substring(0, 3).equals("015")))
//            true
//        else {
//            showAlert(getString(R.string.invalid_mobile))
//            false
//        }
//    }

    override fun showOptionsMenu() {
    }
// endregion

    fun navController() = findNavController()
}
