package com.neqabty.presentation.ui.medicalRenewFollowerDetails

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalRenewFollowerDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.MedicalRenewalUI
import com.neqabty.presentation.ui.medicalCategories.MedicalCategoriesFragmentArgs
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.medical_renew_follower_details_fragment.*
import javax.inject.Inject

class MedicalRenewFollowerDetailsFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<MedicalRenewFollowerDetailsFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors

    lateinit var followerItem: MedicalRenewalUI.FollowerItem

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.medical_renew_follower_details_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initializeViews()
    }

    private fun initializeViews() {
        val params = MedicalRenewFollowerDetailsFragmentArgs.fromBundle(arguments!!)
        binding.followerItem = params.followerItem

        bSave.setOnClickListener {
            val intent = Intent()
            val bundle = Bundle()
//            bundle.putParcelable("companion", companion)
            intent.putExtras(bundle)
            parentFragmentManager?.setFragmentResult("bundle",bundle)
            navController().navigateUp()
        }
    }
    //region
// endregion

    fun navController() = findNavController()
}
