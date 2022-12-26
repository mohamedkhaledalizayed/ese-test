package com.neqabty.healthcare.sustainablehealth.search.presentation.view.providerdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.neqabty.healthcare.core.ui.BaseDialogFragment
import com.neqabty.healthcare.databinding.PhonesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhonesFragment : BaseDialogFragment<PhonesFragmentBinding>() {
    lateinit var phoneNumbers: String
    override fun getViewBinding() = PhonesFragmentBinding.inflate(layoutInflater)

    companion object {

        @JvmStatic
        fun newInstance(phones: String) =
            PhonesFragment().apply {
                arguments = Bundle().apply {
                    putString("phones", phones)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            phoneNumbers = it.getString("phones").toString()
        }

        val numbers = phoneNumbers?.split("-")

        val adapter = PhonesAdapter()
        adapter.onItemClickListener = object : PhonesAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: String) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$item")
                startActivity(intent)
            }
        }
        adapter.submitList(numbers?.toMutableList())
        binding.rvPhones.adapter = adapter
    }
//region

// endregion
}
