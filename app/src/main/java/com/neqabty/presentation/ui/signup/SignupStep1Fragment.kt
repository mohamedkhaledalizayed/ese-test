package com.neqabty.presentation.ui.signup

import android.app.DatePickerDialog
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
import com.neqabty.databinding.Signup1FragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.autoCleared

import java.util.*
import javax.inject.Inject

class SignupStep1Fragment : BaseFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<Signup1FragmentBinding>()

    lateinit var signupViewModel: SignupViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.signup1_fragment,
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

//        initializeObservers()
        initializeViews()
//        loginViewModel.login("01119850766","123@pass" , "c4baf341d52e53c01dd0ff4e2f930ab24886f22c5ef1b15e715534832c0e9528")

//        signupViewModel.signup("m@m.m", "Mona", "Mohamed", "01119850766", "1", "1", "1", "123@pass")
    }
//    private fun handleViewState(state: SignupViewState) {
//        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
//        state.weather?.let {
//        }
//    }

    fun initializeViews() {
        binding.edAge.setOnClickListener { handleAge() }
        val genders = mutableListOf<String>("النوع", "ذكر", "أنثى")
        binding.spGender.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, genders)

//        mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//            }
//
//            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                Toast.makeText(this@MainActivity, myStrings[p2], LENGTH_LONG).show()
//            }
//
//        }
    }

    private fun handleAge() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(activity!!, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            val month = monthOfYear + 1
            binding.edAge.setText("$dayOfMonth/$month/$year")
        }, year, month, day)
        datePickerDialog.show()
    }

//region

// endregion
}
