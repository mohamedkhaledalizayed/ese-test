package com.neqabty.superneqabty.settings

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationManagerCompat
import com.neqabty.signup.core.data.Constants
import com.neqabty.superneqabty.R
import com.neqabty.superneqabty.core.utils.PreferencesHelper
import com.neqabty.superneqabty.databinding.ActivitySettingsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {
    
    private lateinit var toolbar: Toolbar
    private lateinit var binding: ActivitySettingsBinding


    @Inject
    lateinit var sharedPreferences: PreferencesHelper
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        toolbar = findViewById<Toolbar>(R.id.toolbar)
    }


    override fun onResume() {
        super.onResume()
        initializeViews()
    }


    private fun initializeViews() {
        val status = NotificationManagerCompat.from(this).areNotificationsEnabled()
        sharedPreferences.isNotificationsEnabled = status
        binding.switchNotifications.isChecked = status
        binding.switchNotifications.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

                val intent = Intent()
                intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"

                // for Android 5-7
                intent.putExtra("app_package", getPackageName())
                intent.putExtra("app_uid", getApplicationInfo().uid)

                // for Android 8 and above
                intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName())

                startActivityForResult(intent, 0)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            val status = NotificationManagerCompat.from(this).areNotificationsEnabled()
            sharedPreferences.isNotificationsEnabled
            binding.switchNotifications.isChecked = status
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
        startActivityForResult(Intent(Settings.ACTION_SETTINGS), 0)
    }

    private fun Intent.setOpenSettingsForApiLarger25() {
        action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
        putExtra("android.provider.extra.APP_PACKAGE", packageName)
    }

    private fun Intent.setOpenSettingsForApiBetween21And25() {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        putExtra("app_package", packageName)
        putExtra("app_uid", applicationInfo?.uid)
    }

    private fun Intent.setOpenSettingsForApiLess21() {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        addCategory(Intent.CATEGORY_DEFAULT)
        data = Uri.parse("package:" + packageName)
    }
// endregion

}