package com.neqabty.presentation.ui.intro

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neqabty.R
import com.neqabty.databinding.IntroPagerFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.util.autoCleared

class IntroPagerFragment : Fragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<IntroPagerFragmentBinding>()

    companion object {
        fun newInstance(Image: Int, Text: Int) = IntroPagerFragment().apply {
            arguments = Bundle().apply {
                putInt("Image", Image)
                putInt("Text", Text)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.intro_pager_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val imageview = arguments!!.getInt("Image")
        val textview = arguments!!.getInt("Text")

        binding.ivImage.setImageResource(imageview)
        binding.tvPageText.setText(textview)
    }
}
