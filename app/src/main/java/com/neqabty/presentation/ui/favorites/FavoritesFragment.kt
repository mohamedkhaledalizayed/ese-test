package com.neqabty.presentation.ui.favorites

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.MedicalFavoritesFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.autoCleared

import javax.inject.Inject

class FavoritesFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<MedicalFavoritesFragmentBinding>()
    private var adapter by autoCleared<FavoritesAdapter>()

    lateinit var favoritesViewModel: FavoritesViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.medical_favorites_fragment,
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

    private fun handleViewState(state: FavoritesViewState) {
        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
        state.favorites?.let {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }
    }

    fun initializeViews() {
        favoritesViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(FavoritesViewModel::class.java)

        val adapter = FavoritesAdapter(dataBindingComponent, appExecutors, callback = { favoriteItem ->
            navController().navigate(
                    FavoritesFragmentDirections.providerDetails(favoriteItem)
            )
        }, removeCallback = { favoriteItem ->
            favoritesViewModel.removeFavorite(favoriteItem)
        })
        this.adapter = adapter
        binding.rvFavorites.adapter = adapter

        favoritesViewModel.viewState.observe(this, Observer {
            if (it != null) handleViewState(it)
        })
        favoritesViewModel.errorState.observe(this, Observer { _ ->
            showConnectionAlert(requireContext(), retryCallback = {
                binding.progressbar.visibility = View.VISIBLE
                favoritesViewModel.getFavorites()
            }, cancelCallback = {
                navController().navigateUp()
            })
        })

        favoritesViewModel.getFavorites()
    }

//region

// endregion

    fun navController() = findNavController()
}
