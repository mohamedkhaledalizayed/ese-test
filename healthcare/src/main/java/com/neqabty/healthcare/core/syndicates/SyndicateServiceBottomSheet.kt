package com.neqabty.healthcare.core.syndicates

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.core.home_syndicates.view.SyndicateServicesAdapter
import com.neqabty.healthcare.core.home_syndicates.view.SyndicatesHomeViewModel
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.FragmentSyndicateServiceBottomSheetBinding
import com.neqabty.healthcare.syndicates.domain.entity.SyndicateEntity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


private const val SYNDICATE_NAME = "name"
private const val SYNDICATE_CODE = "code"


@AndroidEntryPoint
class SyndicateServiceBottomSheet : BottomSheetDialogFragment() {

    private var syndicateName: String? = null
    private var syndicateCode: String? = null
    private val mAdapter = SyndicateServicesAdapter()

    @Inject
    lateinit var sharedPreferences: PreferencesHelper
    private val syndicatesHomeViewModel: SyndicatesHomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            syndicateName = it.getString(SYNDICATE_NAME)
            syndicateCode = it.getString(SYNDICATE_CODE)
        }
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        val bottomSheetDialog = dialog as BottomSheetDialog

        val view = layoutInflater.inflate(R.layout.fragment_syndicate_service_bottom_sheet, null)
        val binding = FragmentSyndicateServiceBottomSheetBinding.inflate(
            layoutInflater,
            view as ViewGroup,
            false
        )
        bottomSheetDialog.setContentView(binding.root)

        if (sharedPreferences.isSyndicateMember) {
            binding.disclaimer.text = "هذا الرقم مشترك بالفعل فى نقابة ${sharedPreferences.syndicateName}"
        } else {
            binding.signupBtn.text = "الاشتراك في النقابة"
            binding.infoContainer.visibility = View.GONE
        }

        binding.syndicateName.text = syndicateName
        binding.closeBtn.setOnClickListener { dialog.dismiss() }
        binding.rvSyndicateServices.adapter = mAdapter

        val activity = requireActivity() as SyndicatesActivity
        binding.signupBtn.setOnClickListener {
            activity.onSignUpClicked()
            dialog.dismiss()
        }
        binding.backBtn.setOnClickListener { dialog.dismiss() }

        getSyndicateServices()
        observeOnSyndicateServices()

    }

    private fun getSyndicateServices() {
        syndicatesHomeViewModel.getSyndicateServices(syndicateCode ?: "", "")
    }

    private fun observeOnSyndicateServices() {
        syndicatesHomeViewModel.syndicateServices.observe(this) {
            it.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
//                        showProgressDialog()
                    }
                    Status.SUCCESS -> {
//                        hideProgressDialog()
                        if (resource.data != null) {
//                            binding.tvSyndicateServices.visibility = View.VISIBLE
                            mAdapter.submitList(resource.data)
                        }
                    }
                    Status.ERROR -> {
//                        hideProgressDialog()
                    }
                }
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(item: SyndicateEntity) =
            SyndicateServiceBottomSheet().apply {
                arguments = Bundle().apply {
                    putString(SYNDICATE_NAME, item.name)
                    putString(SYNDICATE_CODE, item.code)
                }
            }
    }
}