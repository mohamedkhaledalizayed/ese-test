package com.neqabty.presentation.ui.login

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.neqabty.R
import com.neqabty.databinding.LoginFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.HasHomeOptionsMenu
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.login_fragment.*
import javax.inject.Inject

class LoginFragment : BaseFragment(), Injectable, HasHomeOptionsMenu {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<LoginFragmentBinding>()

    lateinit var loginViewModel: LoginViewModel

    var newToken = ""
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
        loginViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(LoginViewModel::class.java)

        initializeObservers()
        initializeViews()
    }

    private fun initializeObservers() {
        loginViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        loginViewModel.errorState.observe(this, Observer { error ->
//            showConnectionAlert(requireContext(), retryCallback = {
//                login()
//            }, cancelCallback = {
                llSuperProgressbar.visibility = View.GONE
//            }, message = error?.message)
            navController().navigate(LoginFragmentDirections.openLoginWithPasswordFragment(edMobile.text.toString()))
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
                            navController().navigate(LoginFragmentDirections.openLoginWithPasswordFragment(it.mobile))
            } else {// visitor
                PreferencesHelper(requireContext()).mobile = it.mobile
                PreferencesHelper(requireContext()).userType = it.type
                PreferencesHelper(requireContext()).jwt = it.jwt
                Constants.JWT = it.jwt ?: ""
                PreferencesHelper(requireContext()).mainSyndicate = 5
                PreferencesHelper(requireContext()).subSyndicate = 0
                PreferencesHelper(requireContext()).isForceLogout = false
                navController().navigate(LoginFragmentDirections.openHome())
            }
        }
    }

    fun initializeViews() {
        newToken = PreferencesHelper(requireContext()).token
        binding.bSend.setOnClickListener {
            login()
        }
    }

    fun login() {
        if (isDataValid(edMobile.text.toString())) {
            if (PreferencesHelper(requireContext()).token.isNotBlank())
                loginViewModel.login(edMobile.text.toString(), PreferencesHelper(requireContext()).token, PreferencesHelper(requireContext()))
            else {
                FirebaseInstanceId.getInstance().instanceId
                        .addOnCompleteListener(OnCompleteListener { task ->
                            llSuperProgressbar.visibility = View.GONE
                            if (!task.isSuccessful)
                                showAlert("من فضلك تحقق من الإتصال بالإنترنت وحاول مجدداً")
                            else {
                                newToken = task.result?.token!!
                                loginViewModel.login(edMobile.text.toString(), newToken!!, PreferencesHelper(requireContext()))
                            }
                        })
            }
        }
    }
//region

    private fun isDataValid(mobile: String): Boolean {
        return if (mobile.matches(Regex("[0-9]*")) && mobile.trim().length == 11 && (mobile.substring(0, 3).equals("012") || mobile.substring(0, 3).equals("010") || mobile.substring(0, 3).equals("011") || mobile.substring(0, 3).equals("015")))
            true
        else {
            showAlert(getString(R.string.invalid_mobile))
            false
        }
    }
    override fun showOptionsMenu() {
    }
// endregion

    fun navController() = findNavController()
}
