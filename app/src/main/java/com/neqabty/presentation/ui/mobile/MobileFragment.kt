package com.neqabty.presentation.ui.mobile

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
        mobileViewModel.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_LONG).show()
            }
        })
        initializeViews()
    }

    fun initializeViews() {
        binding.bSend.setOnClickListener {
            //TODO validate mobile
            if (PreferencesHelper(requireContext()).token.isNotBlank())
                mobileViewModel.registerUser(binding.edMobile.text.toString(), PreferencesHelper(requireContext()).mainSyndicate, PreferencesHelper(requireContext()).subSyndicate, PreferencesHelper(requireContext()).token, PreferencesHelper(requireContext()))
            else {
                FirebaseInstanceId.getInstance().instanceId
                        .addOnCompleteListener(OnCompleteListener { task ->
                            if (!task.isSuccessful)
                                return@OnCompleteListener
                            val token = task.result?.token
                            mobileViewModel.registerUser(binding.edMobile.toString(), PreferencesHelper(requireContext()).mainSyndicate, PreferencesHelper(requireContext()).subSyndicate, token
                                    ?: "", PreferencesHelper(requireContext()))
                        })
            }

// PreferencesHelper(requireContext()).mobile = binding.edMobile.text.toString()
//            navController().navigate(
//                    MobileFragmentDirections.openClaiming()
//            )
        }
    }

    //fun getToken():String{
//
//    FirebaseInstanceId.getInstance().instanceId
//            .addOnCompleteListener(OnCompleteListener { task ->
//                if (!task.isSuccessful)
//                    return@OnCompleteListener
//                task.result?.token ?: ""
//            })
//
//}
    private fun handleViewState(state: MobileViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (state.isSuccessful)
            navController().navigate(
                    MobileFragmentDirections.openClaiming()
            )
    }
//region


// endregion

    fun navController() = findNavController()
}
