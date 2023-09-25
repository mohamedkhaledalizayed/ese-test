package com.neqabty.healthcare.pharmacymart.orders.ui.orderdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.FragmentCancellationDialogBinding


class CancellationDialog : DialogFragment() {

    private lateinit var binding: FragmentCancellationDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCancellationDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.agree.setOnClickListener {
            if (binding.cancellationReason.text.isEmpty()){
                Toast.makeText(requireContext(), "من فضلك اكتب السبب.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val activity = requireActivity() as PharmacyMartOrderDetailsActivity
            activity.cancelOrder(true, binding.cancellationReason.text.toString())
            dismiss()
        }

        binding.disagree.setOnClickListener { dismiss() }

    }

}