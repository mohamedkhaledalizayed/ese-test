package com.neqabty.healthcare.core.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.neqabty.healthcare.core.utils.disableCopying
import com.neqabty.healthcare.core.utils.forAllChildren


abstract class BaseDialogFragment<B : ViewBinding> : DialogFragment() {

    lateinit var binding: B
    abstract fun getViewBinding(): B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = getViewBinding()
        (binding.root as ViewGroup).forAllChildren { v ->
            when (v) {
                is EditText -> v.disableCopying()
            }
        }
        return binding.root
    }
}