package com.neqabty.presentation.ui.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.location.LocationManager
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.neqabty.R
import com.neqabty.databinding.LoginFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.login_fragment.*

import javax.inject.Inject

class LoginFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<LoginFragmentBinding>()

    lateinit var loginViewModel: LoginViewModel

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
        loginViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(LoginViewModel::class.java)

        initializeObservers()
        initializeViews()
    }

    private fun initializeObservers() {
        loginViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        loginViewModel.errorState.observe(this, Observer { throwable ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                login()
            }, cancelCallback = {
                llSuperProgressbar.visibility = View.GONE
                navController().navigateUp()
            })
//            throwable?.let {
//                Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_LONG).show()
//            }
        })
    }

    private fun handleViewState(state: LoginViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.user?.let {
            PreferencesHelper(requireContext()).mobile = edMobile.text.toString()
            PreferencesHelper(requireContext()).jwt = it.token
            navController().navigate(LoginFragmentDirections.openSyndicatesFragment())
        }
    }

    fun initializeViews() {
        binding.bSend.setOnClickListener {
            login()
        }

//        bSkip.setOnClickListener {
//            PreferencesHelper(requireContext()).mobile = "01119850766"
//            PreferencesHelper(requireContext()).jwt = ""
//            navController().navigate(LoginFragmentDirections.openSyndicatesFragment())
//        }
    }

    fun login(){
        if(isDataValid(edMobile.text.toString())) {
            if (PreferencesHelper(requireContext()).token.isNotBlank())
                loginViewModel.login(edMobile.text.toString(), PreferencesHelper(requireContext()).token, PreferencesHelper(requireContext()))
            else {
                FirebaseInstanceId.getInstance().instanceId
                        .addOnCompleteListener(OnCompleteListener { task ->
                            if (!task.isSuccessful)
                                return@OnCompleteListener
                            val token = task.result?.token
                            loginViewModel.login(edMobile.text.toString(), token!!, PreferencesHelper(requireContext()))
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

    private fun showAlert(msg: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(msg)
        builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, which ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

// endregion

    fun navController() = findNavController()
}
