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
import com.neqabty.databinding.Claiming3FragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

@OpenForTesting
class ClaimingStep3Fragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<Claiming3FragmentBinding>()

    lateinit var claimingViewModel: ClaimingViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.claiming3_fragment,
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
//        loginViewModel.login("03339850766","323@pass" , "c4baf343d52e53c03dd0ff4e2f930ab24886f22c5ef3b35e735534832c0e9528")

//        signupViewModel.signup("m@m.m", "Mona", "Mohamed", "03339850766", "3", "3", "3", "323@pass")

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
//            override fun onItemSelected(p0: AdapterView<*>?, p3: View?, p2: Int, p3: Long) {
//                Toast.makeText(this@MainActivity, myStrings[p2], LENGTH_LONG).show()
//            }
//
//        }
    }

//region


// endregion

}
