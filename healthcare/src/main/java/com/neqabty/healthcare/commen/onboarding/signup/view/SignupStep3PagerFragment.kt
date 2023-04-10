package com.neqabty.healthcare.commen.onboarding.signup.view

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.neqabty.healthcare.R
import com.neqabty.healthcare.commen.eselanding.EseLandingActivity
import com.neqabty.healthcare.commen.landing.view.LandingPageActivity
import com.neqabty.healthcare.commen.landing.view.OurNewsAdapter
import com.neqabty.healthcare.commen.onboarding.signup.data.SignupData
import com.neqabty.healthcare.commen.syndicates.domain.entity.SyndicateEntity
import com.neqabty.healthcare.core.data.Constants.ESE_CODE
import com.neqabty.healthcare.core.data.Constants.NATURAL_THERAPY_CODE
import com.neqabty.healthcare.core.data.Constants.NEQABTY_CODE
import com.neqabty.healthcare.databinding.FragmentSignupStepThreeBinding
import com.neqabty.healthcare.mega.home.view.MegaHomeActivity
import com.neqabty.healthcare.news.domain.entity.NewsEntity
import com.neqabty.healthcare.news.view.newsdetails.NewsDetailsActivity

class SignupStep3PagerFragment : Fragment() {

    private lateinit var binding: FragmentSignupStepThreeBinding

    private val syndicatesAdapter = SyndicatesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupStepThreeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initializeViews()

    }

    private fun initializeViews() {
        binding.bYes.setOnClickListener {
            (it as MaterialButton).strokeColor = ColorStateList.valueOf(R.color.colorAccent)
            (binding.bNo).strokeColor = ColorStateList.valueOf(R.color.gray)
            binding.rvSyndicates.visibility = View.VISIBLE

            binding.rvSyndicates.adapter = syndicatesAdapter
            syndicatesAdapter.submitList(SignupData.syndicatesList)
            syndicatesAdapter.onItemClickListener = object :
                SyndicatesAdapter.OnItemClickListener {
                override fun setOnItemClickListener(item: SyndicateEntity) {
                    SignupData.syndicateID = item.code
                    SignupData.syndicateName = item.name
                    navigateToNextStep()
                }
            }
        }

        binding.bNo.setOnClickListener {
            (it as MaterialButton).strokeColor = ColorStateList.valueOf(R.color.colorAccent)
            (binding.bYes).strokeColor = ColorStateList.valueOf(R.color.gray)
            binding.rvSyndicates.visibility = View.INVISIBLE

            SignupData.syndicateID = NEQABTY_CODE
            SignupData.syndicateName = getString(R.string.neqabty_mega)
            navigateToNextStep()
        }
    }

    private fun navigateToNextStep() {
        (requireActivity() as SignupActivity).binding.slvpSignup.also { pager ->
            pager.setCurrentItem(
                pager.currentItem + 1,
                true
            )
        }
    }

}
