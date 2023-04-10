package com.neqabty.healthcare.commen.onboarding.intro.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.neqabty.healthcare.databinding.FragmentIntroPagerBinding

class IntroPagerFragment : Fragment() {

    private lateinit var binding: FragmentIntroPagerBinding

    companion object {
        fun newInstance(introPagerModel: IntroPagerModel) = IntroPagerFragment().apply {
            arguments = Bundle().apply {
                putInt("Image", introPagerModel.drawableResId)
                putInt("Text", introPagerModel.titleResId)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntroPagerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val imageview = arguments!!.getInt("Image")
        val textview = arguments!!.getInt("Text")

        binding.ivImage.setImageResource(imageview)
    }
}
