package com.neqabty.presentation.ui.inquiryDetails

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.InquiryDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.MemberUI
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject


@OpenForTesting
class InquiryDetailsFragment : BaseFragment(), Injectable {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<InquiryDetailsFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors
    lateinit var memberItem : MemberUI

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.inquiry_details_fragment,
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

    fun initializeViews() {
        val params = InquiryDetailsFragmentArgs.fromBundle(arguments!!)
        memberItem = params.memberItem

        memberItem?.let {
            var tempMember = it.copy()
            tempMember.engineerName = getString(R.string.name_title) + " " + it.engineerName
            tempMember.expirationDate = getString(R.string.expiration_date_title) + " " + it.expirationDate
            tempMember.amount = getString(R.string.amount_title) + " " + it.amount + " ج"
            binding.memberItem = tempMember
        }
    }

// endregion

    fun navController() = findNavController()
}
