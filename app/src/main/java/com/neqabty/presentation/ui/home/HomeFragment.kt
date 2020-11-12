package com.neqabty.presentation.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.BuildConfig
import com.neqabty.R
import com.neqabty.databinding.HomeFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.ui.common.CustomFragmentPagerAdapter
import com.neqabty.presentation.util.HasHomeOptionsMenu
import com.neqabty.presentation.util.OnBackPressedListener
import com.neqabty.presentation.util.PreferencesHelper
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.main_activity.*
import javax.inject.Inject


class HomeFragment : BaseFragment(), Injectable, OnBackPressedListener, HasHomeOptionsMenu {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<HomeFragmentBinding>()

    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var appExecutors: AppExecutors

    var isAlertShown = false
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.home_fragment,
                container,
                false,
                dataBindingComponent
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.mipmap.menu_ic)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(HomeViewModel::class.java)

        homeViewModel.viewState.observe(this.requireActivity(), Observer {
            if (it != null) handleViewState(it)
        })
        homeViewModel.errorState.observe(this, Observer { error ->
            showConnectionAlert(requireContext(), retryCallback = {
                llSuperProgressbar.visibility = View.VISIBLE
                homeViewModel.getContent(PreferencesHelper(requireContext()).mainSyndicate.toString(), PreferencesHelper(requireContext()).user)
            }, cancelCallback = {
                navController().navigateUp()
            }, message = error?.message)
        })

        homeViewModel.getContent(PreferencesHelper(requireContext()).mainSyndicate.toString(), PreferencesHelper(requireContext()).user)

        initializeViews()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun handleViewState(state: HomeViewState) {
        llSuperProgressbar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        state.appVersion?.let {
            if (BuildConfig.VERSION_CODE < it) {
                if (!isAlertShown)
                    showAlert()
            }
        }
        state.notificationsCount?.let {
            PreferencesHelper(requireContext()).notificationsCount = it
            activity?.invalidateOptionsMenu()
        }
    }

    fun initializeViews() {
        (((activity as AppCompatActivity).drawer_layout) as DrawerLayout).setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

        val adapter = CustomFragmentPagerAdapter(childFragmentManager)
        adapter.addFragment(WheelMedicalFragment())
        adapter.addFragment(WheelNewsFragment())
        adapter.addFragment(WheelTripsFragment())
        adapter.addFragment(WheelEmploymentFragment())
        adapter.addFragment(WheelSyndicateServicesFragment())
        adapter.addFragment(WheelTrainingFragment())
        adapter.addFragment(WheelRetireesFragment())
        adapter.addFragment(WheelCustomerServiceFragment())
        adapter.addFragment(WheelComplaintsFragment())
        adapter.addFragment(WheelPaymentsFragment())
        binding.viewpager.adapter = adapter
        binding.viewpager.setSwipePagingEnabled(false)
        binding.viewpager.offscreenPageLimit = 8

        val sectionsList = mutableListOf<String>(getString(R.string.wheel_medical),getString(R.string.wheel_news), getString(R.string.wheel_trips),
                getString(R.string.wheel_employment), getString(R.string.wheel_syndicate_services), getString(R.string.wheel_training), getString(R.string.wheel_retirees),
                getString(R.string.wheel_customer_service), getString(R.string.wheel_complaints), getString(R.string.wheel_payment))

        val iconsList = mutableListOf<Int>( R.drawable.ic_wheel_medical_approval,R.drawable.ic_wheel_news, R.drawable.ic_wheel_trip, R.drawable.ic_wheel_employment,
                R.drawable.ic_wheel_syndicate_services, R.drawable.ic_wheel_training, R.drawable.ic_wheel_retirees,
                R.drawable.ic_wheel_customer_service, R.drawable.ic_wheel_complaints, R.drawable.ic_wheel_payments)

        val wheelAdapter = WheelAdapter(requireContext(), sectionsList, iconsList, 0)
        wheel.setAdapter(wheelAdapter)
        wheel.setOnMenuSelectedListener { parent, view, pos ->
            if ((wheel.adapter as WheelAdapter).selectedItemPosition != pos) {
                wheelAdapter.selectedItemPosition = pos
                wheel.setAdapter(wheelAdapter)
                tvTopTitle.setText(sectionsList[pos])
                tvBottomTitle.setText(sectionsList[pos])
                viewpager.setCurrentItem(pos, true)
                viewpager.animateViewPager(viewpager, 3, 200)
            }
            else
                viewpager.setCurrentItem(pos, true)
        }

        wheel.setSelection(0)

        ibNext.setOnClickListener{
            if(wheel.selectedPosition == iconsList.size - 1)
                wheel.setSelection(0)
            else
                wheel.setSelection(wheel.selectedPosition + 1)
        }
        ibPrev.setOnClickListener{
            if(wheel.selectedPosition == 0)
                wheel.setSelection(iconsList.size - 1)
            else
                wheel.setSelection(wheel.selectedPosition - 1)
        }
    }

    override fun onBackPressed() {
    }

    override fun showOptionsMenu() {
    }

    //region
    private fun showAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(getString(R.string.update_msg))
        builder.setCancelable(false)
        builder.setPositiveButton(getString(R.string.ok_btn)) { dialog, which ->
            val appPackageName = requireContext().packageName
            try {
                startActivity(
                        Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=$appPackageName")
                        )
                )
            } catch (anfe: android.content.ActivityNotFoundException) {
                startActivity(
                        Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                        )
                )
            }
            showAlert()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
        isAlertShown = true
    }
// endregion

    fun navController() = findNavController()
}
