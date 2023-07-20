package com.neqabty.healthcare.medicalnetwork.presentation.view.providerdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.neqabty.healthcare.databinding.PhonesFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhonesFragment : DialogFragment() {
    lateinit var phoneNumbers: String
    private lateinit var binding: PhonesFragmentBinding

    companion object {

        @JvmStatic
        fun newInstance(phones: String) =
            PhonesFragment().apply {
                arguments = Bundle().apply {
                    putString("phones", phones)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PhonesFragmentBinding.inflate(layoutInflater)
        return binding.root
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
