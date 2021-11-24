package com.neqabty.presentation.ui.medicalRenewUpdate

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalRenewUpdateFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.medical_renew_fragment.llContent
import kotlinx.android.synthetic.main.medical_renew_fragment.rvFollowers
import kotlinx.android.synthetic.main.medical_renew_update_fragment.*
import kotlinx.android.synthetic.main.medical_renew_update_fragment.view.*
import javax.inject.Inject

@AndroidEntryPoint
class MedicalRenewUpdateFragment : BaseFragment() {
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<MedicalRenewUpdateFragmentBinding>()
    private var adapter by autoCleared<FollowersUpdateAdapter>()

    private val medicalRenewUpdateViewModel: MedicalRenewUpdateViewModel by viewModels()

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

        medicalRenewUpdateViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        medicalRenewUpdateViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                medicalRenewUpdateViewModel.getMedicalRenewalData(sharedPref.mobile, sharedPref.user)
            }, cancelCallback = {
                dialog?.dismiss()
            }, message = error?.message)
        })

        medicalRenewUpdateViewModel.getMedicalRenewalData(sharedPref.mobile, sharedPref.user)
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

        binding.root.bAddFollower.visibility = if (Constants.isHealthCareProjectEnabled) View.VISIBLE else View.GONE
        bAddFollower.setOnClickListener {
            isEdit = false
            goToEditFollower()
        }
        bSubmit.setOnClickListener {
            bSubmit.isEnabled = false
            sendRequestConfirmation()
            bSubmit.isEnabled = true
        }
        updateFollowersTitleVisibility()
        updateSubmitBtnState()
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
            state.medicalRenewalUI?.oldRefId = sharedPref.user
//            filteredFollowersList = state.medicalRenewalUI?.followers?.filter{ it.isDeleted == false }!!.toMutableList()
            medicalRenewalUI = state.medicalRenewalUI!!
            initializeViews()
        }
    }

    //region
    private fun removeFollower(follower: MedicalRenewalUI.FollowerItem) {
        parentFragmentManager.setFragmentResultListener("bundle", this, FragmentResultListener { key, result ->
            val deletedFollower =
                result.getParcelable<MedicalRenewalUI.FollowerItem>("followerItem")!!
            medicalRenewalUI.followers?.find { it.id == deletedFollower.id }?.isDeleted = true
            medicalRenewalUI.followers?.find { it.id == deletedFollower.id }?.isEdited = false
            medicalRenewalUI.followers?.find { it.id == deletedFollower.id }?.modificationReason = deletedFollower.modificationReason
            medicalRenewalUI.followers?.find { it.id == deletedFollower.id }?.attachments = deletedFollower.attachments
            adapter.submitList(medicalRenewalUI.followers?.filter { it.isDeleted == false })
            adapter.notifyDataSetChanged()
            rvFollowers.adapter = adapter

            updateFollowersTitleVisibility()
            updateSubmitBtnState()
        })

        selectedFollower = follower
        if(selectedFollower.isNew == true) {
            medicalRenewalUI.followers?.removeAll { it.id == selectedFollower.id }
            updateFollowersTitleVisibility()
            updateSubmitBtnState()
        }
        else
            navController().navigate(MedicalRenewUpdateFragmentDirections.openMedicalRenewRemoveFollowerDetailsFragment(selectedFollower))
    }

    private fun sendRequestConfirmation() {
        builder = AlertDialog.Builder(requireContext())
        builder?.setTitle(getString(R.string.alert_title))
        builder?.setMessage(getString(R.string.send_request_confirmation))
        builder?.setPositiveButton(getString(R.string.alert_confirm)) { dialog, which ->
            if (medicalRenewalUI.followers?.size == 0) {
                showAlert(getString(R.string.medical_subscription_renew_add_follower))
            }else if(medicalRenewalUI.followers?.filter { it.isDeleted == false && it.relationType == "2" && it.gender == "F"}?.size!! > 4)
                showAlert(getString(R.string.exceed_wives))
            else if(medicalRenewalUI.followers?.filter { it.isDeleted == false && it.relationType == "2" && it.gender == "M"}?.size!! > 1)
                showAlert(getString(R.string.exceed_husband))
            else if(medicalRenewalUI.followers?.filter { it.isDeleted == false && it.relationType == "4" && it.gender == "F"}?.size!! > 1)
                showAlert(getString(R.string.exceed_mother))
            else if(medicalRenewalUI.followers?.filter { it.isDeleted == false && it.relationType == "4" && it.gender == "M"}?.size!! > 1)
                showAlert(getString(R.string.exceed_father))
            else {
                updateRequested = true
                medicalRenewUpdateViewModel.updateMedicalRenewalData(sharedPref.mobile, medicalRenewalUI)
            }
            dialog.dismiss()
        }
        builder?.setNegativeButton(getString(R.string.alert_no)) { dialog, which ->
            dialog.dismiss()
        }

        var dialog = builder?.create()
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
            updateSubmitBtnState()
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


    private fun updateSubmitBtnState() {
        bSubmit.isEnabled = medicalRenewalUI.followers?.filter { (it.isNew == true && it.isDeleted == false) ||  (it.isNew == false && it.isDeleted == true) || it.isEdited == true}?.size != 0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bSubmit.backgroundTintList = if(medicalRenewalUI.followers?.filter { (it.isNew == true && it.isDeleted == false) ||  (it.isNew == false && it.isDeleted == true) || it.isEdited == true }?.size != 0) null else ColorStateList.valueOf(resources.getColor(R.color.gray))
        }
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
