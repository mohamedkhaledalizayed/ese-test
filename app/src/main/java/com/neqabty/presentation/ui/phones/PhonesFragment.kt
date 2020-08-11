package com.neqabty.presentation.ui.phones

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.PhonesFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.autoCleared

import kotlinx.android.synthetic.main.subsyndicates_fragment.*
import javax.inject.Inject

class PhonesFragment : DialogFragment(), Injectable {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<PhonesFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors

    lateinit var phoneNumbers: String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.phones_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val bundle = this.arguments
        bundle?.let { phoneNumbers = it.getString("phones") }
        val phoneNumbers = phoneNumbers.replace(" ", "").replace("\r\n", "-")
                .replace('\n', '-').replace('\r', '-').split("-")

        val adapter = PhonesAdapter(dataBindingComponent, appExecutors)
        adapter.submitList(phoneNumbers)
        binding.rvPhones.adapter = adapter
    }

//region

// endregion
}
