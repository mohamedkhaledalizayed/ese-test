package com.neqabty.presentation.ui.mobile

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.neqabty.R
import com.neqabty.databinding.MobileFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject


@OpenForTesting
class MobileFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<MobileFragmentBinding>()

    lateinit var mobileViewModel: MobileViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.mobile_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mobileViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MobileViewModel::class.java)

        mobileViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        mobileViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(),retryCallback =  {
                binding.progressbar.visibility = View.VISIBLE
                mobileViewModel.registerUser(binding.edMobile.text.toString(), PreferencesHelper(requireContext()).mainSyndicate, PreferencesHelper(requireContext()).subSyndicate, PreferencesHelper(requireContext()).token, PreferencesHelper(requireContext()), PreferencesHelper(requireContext()).user)
            }, cancelCallback = {
                navController().navigateUp()
            })
        })
        initializeViews()
    }

    fun initializeViews() {
        binding.bSend.setOnClickListener {
            if (isDataValid(binding.edMobile.text.toString(), binding.edMemberNumber.text.toString())) {
                PreferencesHelper(requireContext()).user = binding.edMemberNumber.text.toString()
                if (PreferencesHelper(requireContext()).token.isNotBlank())
                    mobileViewModel.registerUser(binding.edMobile.text.toString(), PreferencesHelper(requireContext()).mainSyndicate, PreferencesHelper(requireContext()).subSyndicate, PreferencesHelper(requireContext()).token, PreferencesHelper(requireContext()), PreferencesHelper(requireContext()).user)
                else {
                    FirebaseInstanceId.getInstance().instanceId
                            .addOnCompleteListener(OnCompleteListener { task ->
                                if (!task.isSuccessful)
                                    return@OnCompleteListener
                                val token = task.result?.token
                                mobileViewModel.registerUser(binding.edMobile.toString(), PreferencesHelper(requireContext()).mainSyndicate, PreferencesHelper(requireContext()).subSyndicate, token
                                        ?: "", PreferencesHelper(requireContext()), PreferencesHelper(requireContext()).user)
                            })
                }
            }
        }
    }

    private fun handleViewState(state: MobileViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (state.isSuccessful) {
            hideKeyboard()
            activity?.invalidateOptionsMenu()
            navController().navigate(
                    MobileFragmentDirections.openClaiming()
            )
        }
    }
//region
    private fun isDataValid(mobile : String, number: String): Boolean {
        return if(number.isBlank()){
            showAlert(getString(R.string.invalid_data))
            false
        }else if (mobile.matches(Regex("[0-9]*")) && mobile.trim().length == 11 && (mobile.substring(0, 3).equals("012") || mobile.substring(0, 3).equals("010") || mobile.substring(0, 3).equals("011") || mobile.substring(0, 3).equals("015")))
            true
        else {
            showAlert(getString(R.string.invalid_mobile))
            false
        }
    }

    private fun showAlert(msg : String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(msg)
        builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, which ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun hideKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.edMobile.windowToken, 0)
    }
// endregion

    fun navController() = findNavController()
}
