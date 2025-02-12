package com.neqabty.presentation.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.SettingsFragmentBinding
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.common.BaseFragment
import com.neqabty.presentation.util.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : BaseFragment() {

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<SettingsFragmentBinding>()

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.settings_fragment,
                container,
                false,
                dataBindingComponent
        )

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initializeViews()
    }

    private fun initializeViews() {
        sharedPref.isNotificationsEnabled = NotificationManagerCompat.from(context!!).areNotificationsEnabled()
        binding.switchNotifications.isChecked = sharedPref.isNotificationsEnabled
        binding.switchNotifications.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

                val intent = Intent()
                intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"

                // for Android 5-7
                intent.putExtra("app_package", activity!!.getPackageName())
                intent.putExtra("app_uid", activity!!.getApplicationInfo().uid)

                // for Android 8 and above
                intent.putExtra("android.provider.extra.APP_PACKAGE", activity!!.getPackageName())

                startActivityForResult(intent, 0)
            }
        })

        binding.seekbar.progress = when(sharedPref.fontSize){
            "small" -> 1
            "large" -> 3
            else -> 2
        }

        binding.seekbar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                when(p1){
                    1 -> {
                        sharedPref.fontSize = "small"
                        requireActivity().setTheme(R.style.AppTheme_NoActionBar_SmallText)
                    }
                    3 -> {
                        sharedPref.fontSize = "large"
                        requireActivity().setTheme(R.style.AppTheme_NoActionBar_LargeText)
                    }
                    else -> {
                        sharedPref.fontSize = "medium"
                        requireActivity().setTheme(R.style.AppTheme_NoActionBar)
                    }
                }
                navController().navigate(R.id.settingsFragment, arguments,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.settingsFragment, true)
                        .build()
                )
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
                sharedPref.isNotificationsEnabled = NotificationManagerCompat.from(context!!).areNotificationsEnabled()
                binding.switchNotifications.isChecked = sharedPref.isNotificationsEnabled
        }
    }

    //region
    fun openNotificationsSettings() {
        val intent = Intent()
        when {
            Build.VERSION.SDK_INT > Build.VERSION_CODES.O -> intent.setOpenSettingsForApiLarger25()
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> intent.setOpenSettingsForApiBetween21And25()
            else -> intent.setOpenSettingsForApiLess21()
        }
        activity!!.startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0)
    }

    private fun Intent.setOpenSettingsForApiLarger25() {
        action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
        putExtra("android.provider.extra.APP_PACKAGE", activity!!.packageName)
    }

    private fun Intent.setOpenSettingsForApiBetween21And25() {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        putExtra("app_package", activity!!.packageName)
        putExtra("app_uid", activity!!.applicationInfo?.uid)
    }

    private fun Intent.setOpenSettingsForApiLess21() {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        addCategory(Intent.CATEGORY_DEFAULT)
        data = Uri.parse("package:" + activity!!.packageName)
    }
// endregion

    fun navController() = findNavController()
}
