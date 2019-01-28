package com.neqabty.presentation.ui.notificationDetails

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.neqabty.R
import com.neqabty.databinding.NotificationDetailsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.entities.NotificationUI
import com.neqabty.presentation.util.autoCleared
import com.neqabty.testing.OpenForTesting


@OpenForTesting
class NotificationDetailsFragment : BaseFragment(), Injectable {
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<NotificationDetailsFragmentBinding>()

    lateinit var notificationItem : NotificationUI

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.notification_details_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val params = NotificationDetailsFragmentArgs.fromBundle(arguments!!)
        notificationItem = params.notificationItem

        initializeViews()

    }

    fun initializeViews() {
        binding.notificationItem = notificationItem
    }


//region


// endregion

    fun navController() = findNavController()
}
