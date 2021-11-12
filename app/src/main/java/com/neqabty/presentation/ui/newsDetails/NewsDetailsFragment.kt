package com.neqabty.presentation.ui.newsDetails

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.NewsDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.entities.NewsUI
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailsFragment : BaseFragment() {
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<NewsDetailsFragmentBinding>()

    lateinit var newsItem: NewsUI

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.news_details_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val params = NewsDetailsFragmentArgs.fromBundle(arguments!!)
        newsItem = params.newsItem

        initializeViews()
    }

    fun initializeViews() {
        binding.newsItem = newsItem
        binding.tvDesc.movementMethod = LinkMovementMethod.getInstance()
    }

//region

// endregion

    fun navController() = findNavController()
}
