package com.neqabty.meganeqabty.settings

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.SeekBar
import androidx.core.app.NotificationManagerCompat
import com.neqabty.core.ui.BaseActivity
import com.neqabty.meganeqabty.R
import com.neqabty.meganeqabty.databinding.ActivitySettingsBinding
import com.neqabty.meganeqabty.home.view.homescreen.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : BaseActivity<ActivitySettingsBinding>() {
    
    override fun getViewBinding() = ActivitySettingsBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.settings_title)
        if (sharedPreferences.language == "ar"){
            binding.toggleGroup.check(R.id.btn_arabic)
            binding.language.text = "اللغة"
        }else{
            binding.toggleGroup.check(R.id.btn_english)
            binding.language.text = "Language"
        }
        binding.btnArabic.setOnClickListener {
            sharedPreferences.language = "ar"
            binding.toggleGroup.check(R.id.btn_arabic)
            finish()
            startActivity(Intent(this, SettingsActivity::class.java))

        }

        binding.btnEnglish.setOnClickListener {
            sharedPreferences.language = "en"
            binding.toggleGroup.check(R.id.btn_english)
            finish()
            startActivity(Intent(this, SettingsActivity::class.java))

        }
    }

    override fun onBackPressed() {
        finish()
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

        binding.seekbar.progress = when(sharedPreferences.fontSize){
            "small" -> 1
            "large" -> 3
            else -> 2
        }

        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                when (p1) {
                    1 -> {
                        sharedPreferences.fontSize = "small"
                    }
                    3 -> {
                        sharedPreferences.fontSize = "large"
                    }
                    else -> {
                        sharedPreferences.fontSize = "medium"
                    }
                }
                setTheme(getAppTheme())
                startActivity(Intent(this@SettingsActivity, HomeActivity::class.java))
                finishAffinity()
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