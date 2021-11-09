package com.neqabty.presentation.ui.news

import android.content.Context
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.NewsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared

import javax.inject.Inject

class NewsFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<NewsFragmentBinding>()
    private var adapter by autoCleared<NewsAdapter>()

    lateinit var newsViewModel: NewsViewModel

    @Inject
    lateinit var appExecutors: AppExecutors


    override fun onAttach(context: Context) {
        super.onAttach(context)
        showAds(Constants.AD_NEWS)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.news_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newsViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NewsViewModel::class.java)

        initializeViews()

        val adapter = NewsAdapter(dataBindingComponent, appExecutors) { newsItem ->
            navController().navigate(
                    NewsFragmentDirections.newsDetails(newsItem)
            )
        }
        this.adapter = adapter
        binding.rvNews.adapter = adapter

        newsViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        newsViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                newsViewModel.getNews(sharedPref.mainSyndicate.toString())
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })

        newsViewModel.getNews(sharedPref.mainSyndicate.toString())
    }

    private fun handleViewState(state: NewsViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.news?.let {
            adapter.submitList(it)
        }
    }

    fun initializeViews() {
    }

//region

// endregion

    fun navController() = findNavController()
}
