package com.neqabty.shealth.sustainablehealth.home.presentation.view.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.neqabty.shealth.databinding.FragmentAboutBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AboutFragment : DialogFragment() {

    // TODO: Rename and change types of parameters
    private var title: String? = null
    private var content: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_PARAM1)
            content = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var binding: FragmentAboutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAboutBinding.inflate(layoutInflater)

        binding.title.text = title
        binding.content.text = content

        binding.close.setOnClickListener { dismiss() }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AboutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}