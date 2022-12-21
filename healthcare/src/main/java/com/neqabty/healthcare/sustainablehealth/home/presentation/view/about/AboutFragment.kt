package com.neqabty.healthcare.sustainablehealth.home.presentation.view.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.FragmentAboutBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

class AboutFragment : DialogFragment() {

    // TODO: Rename and change types of parameters
    private var title: String? = null
    private var content: String? = null
    private var code: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_PARAM1)
            content = it.getString(ARG_PARAM2)
            code = it.getString(ARG_PARAM3)
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

        when (code) {
            "P8152" -> {
                binding.image.setImageResource(R.drawable.image_57)
            }
            "P7356" -> {
                binding.image.setImageResource(R.drawable.image_bro)
            }
            "P5906" -> {
                binding.image.setImageResource(R.drawable.silver_image)
            }
            "P5421" -> {
                binding.image.setImageResource(R.drawable.image_plat)
            }
            "P5280" -> {
                binding.image.setImageResource(R.drawable.image_58)
            }
        }

        binding.close.setOnClickListener { dismiss() }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String) =
            AboutFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                }
            }
    }

}