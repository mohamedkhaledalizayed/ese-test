package com.neqabty.presentation.ui.claiming

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.neqabty.R
import com.neqabty.databinding.Claiming2FragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class ClaimingStep2Fragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<Claiming2FragmentBinding>()

    lateinit var claimingViewModel: ClaimingViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.claiming2_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        claimingViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(ClaimingViewModel::class.java)

//        initializeObservers()
        initializeViews()
//        loginViewModel.login("02229850766","222@pass" , "c4baf242d52e52c02dd0ff4e2f920ab24886f22c5ef2b25e725524822c0e9528")

//        signupViewModel.signup("m@m.m", "Mona", "Mohamed", "02229850766", "2", "2", "2", "222@pass")

    }
//    private fun handleViewState(state: SignupViewState) {
//        binding.progressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
//        state.weather?.let {
//        }
//    }

    fun initializeViews() {
        val genders = mutableListOf<String>("النوع","ذكر", "أنثى")
        binding.spGender.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, genders)

//        mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//            }
//
//            override fun onItemSelected(p0: AdapterView<*>?, p2: View?, p2: Int, p2: Long) {
//                Toast.makeText(this@MainActivity, myStrings[p2], LENGTH_LONG).show()
//            }
//
//        }
    }

//region


// endregion

}
