package com.neqabty.shealth.sustainablehealth.search.presentation.view.providerdetails

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.neqabty.shealth.databinding.ActivityProviderDetailsBinding
import com.neqabty.shealth.core.ui.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProviderDetailsActivity : BaseActivity<ActivityProviderDetailsBinding>() {

    private val mAdapter = ReviewsAdapter()
    override fun getViewBinding() = ActivityProviderDetailsBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "دكتور محمد حسن")

        binding.reviewRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            ReviewsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: String) {
            }

            override fun setOnRegisterClickListener(item: String) {
            }
        }

        binding.addReviewBtn.setOnClickListener {
            val fm: FragmentManager = supportFragmentManager
            val dialog = AddReviewDailog()
            dialog.show(fm, "")
            dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth)
        }
    }
}