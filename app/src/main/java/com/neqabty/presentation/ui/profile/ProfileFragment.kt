package com.neqabty.presentation.ui.profile

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
import com.neqabty.databinding.ProfileFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.util.autoCleared
import com.neqabty.presentation.util.loadSVG
import com.neqabty.presentation.util.loadString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<ProfileFragmentBinding>()
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.profile_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        profileViewModel.viewState.observe(this.requireActivity(), Observer {
            if (it != null) handleViewState(it)
        })
        profileViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE

                profileViewModel.getProfile(sharedPref.mobile, sharedPref.user)
            }, cancelCallback = {
                dialog?.dismiss()
            }, message = error?.message)
        })

        profileViewModel.getProfile(sharedPref.mobile, sharedPref.user)
    }

    private fun initializeViews(){
    }

    private fun handleViewState(state: ProfileViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        if (!state.isLoading && state.profile != null) {
            binding.svContent.visibility = View.VISIBLE
            binding.ivQR.loadSVG(state.profile!!.code!!)
            binding.ivEngPhoto.loadString(state.profile!!.image!!)

            sharedPref.photo = state.profile!!.image ?: ""

            binding.tvName.text = state.profile!!.name!!
            binding.tvMobile.text = sharedPref.mobile
            binding.tvNumber.text = sharedPref.user
            binding.tvSyndicate.text = state.profile!!.syndicate!!
            binding.tvSection.text = state.profile!!.section!!
            binding.tvEPoints.setOnClickListener { navController().navigate(R.id.medicalBuyServiceFragment) }
            state.profile!!.invitations.toString().also { binding.tvInvitations.text = it }
            setupClub()
            return
        }
    }

//region
    private fun setupClub() {
        Constants.isClubEnabled.value?.let {
            if (it) {
                binding.tvInvitationsTitle.visibility = View.VISIBLE
                binding.tvInvitations.visibility = View.VISIBLE
                binding.lineView5.visibility = View.VISIBLE
                return
            }
        }

        Constants.isClubEnabled.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.tvInvitationsTitle.visibility = View.VISIBLE
                binding.tvInvitations.visibility = View.VISIBLE
                binding.lineView5.visibility = View.VISIBLE
                return@Observer
            }
        })
    }
// endregion
    fun navController() = findNavController()
}
