package com.neqabty.presentation.ui.syndicates

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
import com.neqabty.databinding.SyndicatesFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.ui.signup.SignupViewModel
import com.neqabty.presentation.ui.signup.SignupViewState
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class SyndicatesFragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<SyndicatesFragmentBinding>()
    private var adapter by autoCleared<SyndicatesAdapter>()

    lateinit var signupViewModel: SignupViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        setupToolbar(true)
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.syndicates_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        signupViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(SignupViewModel::class.java)

        initializeViews()


        val adapter = SyndicatesAdapter(dataBindingComponent, appExecutors) { history ->
//            navController().navigate(
////                    SyndicatesFragmentDirections.showImage(history.path!!)
//            )
        }
        this.adapter = adapter
        binding.rvSyndicates.adapter = adapter
//                adapter.submitList(historyList)
    }

    private fun handleViewState(state: SignupViewState) {
//        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
//        state.user?.let {
//        }
    }

    fun initializeViews() {
    }


//region


// endregion

    fun navController() = findNavController()
}
