package com.neqabty.presentation.ui.updateDataVerification

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.UpdateDataVerificationFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.InquireUpdateUserDataUI
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
    lateinit var userDataInquire: InquireUpdateUserDataUI
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
        updateDataVerificationViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                updateDataVerificationViewModel.updateUserData(userDataInquire.oldRefID,userDataInquire.fullName!!,userDataInquire.nationalID!!,"male",userDataInquire.oldRefID)
            }, cancelCallback = {
                navController().navigateUp()
            })
        })
        initializeViews()
    }

    private fun handleViewState(state: UpdateDataVerificationViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        activity?.invalidateOptionsMenu()
        if (!state.isLoading && state.message != "") {
            showSuccessAlert(getString(R.string.confirm_reservation_msg))
        }
    }

    fun initializeViews() {
        val params = UpdateDataVerificationFragmentArgs.fromBundle(arguments!!)
        userDataInquire = params.userDataInquire

        verificationCode = params.code
//        userDataInquire?.let {
////            var tempMember = it.copy()
////            tempMember.engineerName = getString(R.string.name_title) + " " + it.engineerName
////            tempMember.expirationDate = getString(R.string.expiration_date_title) + " " + it.billDate
////            tempMember.amount = getString(R.string.amount_title) + " " + it.amount + " ج"
//            binding.userDataInquire = it
//        }

        bSend.setOnClickListener {
            if(binding.edVerificationNumber.text.toString() == verificationCode)
                updateDataVerificationViewModel.updateUserData(userDataInquire.oldRefID,userDataInquire.fullName!!,userDataInquire.nationalID!!,"male",userDataInquire.oldRefID)
            else
                showErrorAlert(getString(R.string.wrong_verification_code))
        }
    }



    fun showSuccessAlert(message: String) {
        builder = AlertDialog.Builder(requireContext())
        builder?.setTitle(getString(R.string.thanks))
        builder?.setMessage(message)
        builder?.setCancelable(false)
        builder?.setPositiveButton(getString(R.string.ok_btn)) { dialog, which ->
            navController().popBackStack()
            navController().navigate(R.id.homeFragment)
        }
        var dialog = builder?.create()
        dialog?.show()
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
