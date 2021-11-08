package com.neqabty.presentation.ui.newsDetails

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.NewsDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.NewsUI
import com.neqabty.presentation.util.autoCleared

class NewsDetailsFragment : BaseFragment(), Injectable {
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

        binding.webView.settings.loadsImagesAutomatically = true
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.isLongClickable = true
        binding.webView.setOnLongClickListener { return@setOnLongClickListener true }
        binding.webView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        val justify = "<html><body style='direction:rtl;text-align:justify;'>${newsItem.desc.toString()}</body></html>"
        binding.webView.loadDataWithBaseURL(null, justify, "text/html; charset=utf-8", "UTF-8", null)

    }

//region

// endregion

    fun navController() = findNavController()
}
