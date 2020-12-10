package com.neqabty.presentation.ui.medicalRenewUpdate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalRenewUpdateFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.medical_renew_fragment.llContent
import kotlinx.android.synthetic.main.medical_renew_fragment.rvFollowers
import kotlinx.android.synthetic.main.medical_renew_update_fragment.*
import javax.inject.Inject

class MedicalRenewUpdateFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<MedicalRenewUpdateFragmentBinding>()
    private var adapter by autoCleared<FollowersUpdateAdapter>()

    @Inject
    lateinit var medicalRenewUpdateViewModel: MedicalRenewUpdateViewModel
    var filteredFollowersList = mutableListOf<MedicalRenewalUI.FollowerItem>()

    lateinit var medicalRenewalUI: MedicalRenewalUI

    lateinit var selectedFollower: MedicalRenewalUI.FollowerItem

    @Inject
    lateinit var appExecutors: AppExecutors
    var isEdit = true
    var incrementedID = 2

    var updateRequested = false

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.medical_renew_update_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        medicalRenewUpdateViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MedicalRenewUpdateViewModel::class.java)

        medicalRenewUpdateViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        medicalRenewUpdateViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                medicalRenewUpdateViewModel.getMedicalRenewalData(PreferencesHelper(requireContext()).mobile, PreferencesHelper(requireContext()).user)
            }, cancelCallback = {
                navController().popBackStack()
                navController().navigate(R.id.homeFragment)
            }, message = error?.message)
        })

        medicalRenewUpdateViewModel.getMedicalRenewalData(PreferencesHelper(requireContext()).mobile, PreferencesHelper(requireContext()).user)
    }

    private fun initializeViews() {
        binding.medicalRenewalData = medicalRenewalUI
        llContent.visibility = View.VISIBLE

        val adapter = FollowersUpdateAdapter(dataBindingComponent, appExecutors,
                removeCallback = { follower ->
                    removeFollower(follower)
                },
                editCallback = { follower ->
                    editFollower(follower)
                })
        this.adapter = adapter
        binding.rvFollowers.adapter = adapter
        medicalRenewalUI.followers?.let {
            adapter.submitList(it.filter { it.isDeleted == false }.toMutableList())
        }

        bAddFollower.setOnClickListener {
            isEdit = false
            goToEditFollower()
        }
        bSubmit.setOnClickListener {
            if (medicalRenewalUI.followers?.size == 0) {
                showAlert(getString(R.string.medical_subscription_renew_add_follower))
            } else {
                updateRequested = true
                medicalRenewUpdateViewModel.updateMedicalRenewalData(PreferencesHelper(requireContext()).mobile, medicalRenewalUI)
            }
        }
        updateFollowersTitleVisibility()
    }

    private fun handleViewState(state: MedicalRenewUpdateViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        if (!state.isLoading && updateRequested)
            state.medicalRenewalUpdateUI?.let {
                updateRequested = false
                if (it.requestID.equals("1"))
                    showSuccessAlert()
            }
        state.medicalRenewalUI?.let {
            state.medicalRenewalUI?.oldRefId = PreferencesHelper(requireContext()).user
//            filteredFollowersList = state.medicalRenewalUI?.followers?.filter{ it.isDeleted == false }!!.toMutableList()
            medicalRenewalUI = state.medicalRenewalUI!!
            initializeViews()
        }
    }

    //region
    private fun removeFollower(follower: MedicalRenewalUI.FollowerItem) {
        selectedFollower = follower
        builder = AlertDialog.Builder(requireContext())
        builder?.setTitle(getString(R.string.alert_title))
        builder?.setMessage(getString(R.string.remove_follower_confirmation))
        builder?.setPositiveButton(getString(R.string.alert_confirm)) { dialog, which ->
            medicalRenewalUI.followers?.find { it.id == selectedFollower.id }?.isDeleted = true
            adapter.submitList(medicalRenewalUI.followers?.filter { it.isDeleted == false })
            adapter.notifyDataSetChanged()
            rvFollowers.adapter = adapter

            updateFollowersTitleVisibility()
        }
        builder?.setNegativeButton(getString(R.string.alert_no)) { dialog, which ->
            dialog.dismiss()
        }

        if (dialog == null)
            dialog = builder?.create()

        if (!dialog?.isShowing!!)
            dialog?.show()
    }

    private fun editFollower(follower: MedicalRenewalUI.FollowerItem) {
        selectedFollower = follower
        isEdit = true
//        if(selectedFollower.isNew)
        navController().navigate(MedicalRenewUpdateFragmentDirections.openMedicalRenewAddFollowerDetailsFragment(selectedFollower, medicalRenewalUI))
//        else
//            navController().navigate(MedicalRenewUpdateFragmentDirections.openMedicalRenewFollowerDetailsFragment(selectedFollower))
    }

    private fun goToEditFollower() {
        parentFragmentManager.setFragmentResultListener("bundle", this, FragmentResultListener { key, result ->
            val updatedFollower = result.getParcelable<MedicalRenewalUI.FollowerItem>("followerItem")!!
            if (isEdit) {
                medicalRenewalUI.followers?.replaceAll { followerItem ->
                    if (followerItem.id == updatedFollower.id)
                        updatedFollower
                    else
                        followerItem
                }

            } else {
                if (medicalRenewalUI.followers?.find { it.id == updatedFollower.id } == null)
                    medicalRenewalUI.followers?.add(updatedFollower)
            }
            isEdit = false
            updateFollowersTitleVisibility()
        })

//        if (isEdit) {
//            navController().navigate(MedicalRenewUpdateFragmentDirections.openMedicalRenewFollowerDetailsFragment(selectedFollower))
//        } else {
        incrementedID += 22
        navController().navigate(MedicalRenewUpdateFragmentDirections.openMedicalRenewAddFollowerDetailsFragment(MedicalRenewalUI.FollowerItem(id = incrementedID, isNew = true), medicalRenewalUI))
//        }
    }

    private fun updateFollowersTitleVisibility() {
        binding.tvFollowers.visibility = if (medicalRenewalUI.followers?.filter { it.isDeleted == false }?.size == 0) View.INVISIBLE else View.VISIBLE
    }

    fun showSuccessAlert() {
        builder = AlertDialog.Builder(requireContext())
        builder?.setTitle(getString(R.string.thanks))
        builder?.setMessage(getString(R.string.confirm_followers_edit_msg))
        builder?.setCancelable(false)
        builder?.setPositiveButton(getString(R.string.ok_btn)) { dialog, which ->
            navController().popBackStack()
            navController().navigate(R.id.homeFragment)
        }
        var dialog = builder?.create()
        dialog?.show()
    }
// endregion

    fun navController() = findNavController()
}
