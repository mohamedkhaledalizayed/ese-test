package com.neqabty.courses.offers.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.courses.databinding.FragmentChangeDateDialogBinding


class ChangeDateDialog : DialogFragment() {

    private lateinit var binding: FragmentChangeDateDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeDateDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as OfferDetailsActivity
        binding.changeDate.setOnClickListener {

            if (binding.note.text.toString().isNullOrEmpty()){
                Toast.makeText(requireContext(), "من فضلك قم بكتابة ملاحظة.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            activity.onClick(binding.note.text.toString())
            dismiss()
        }

    }

}