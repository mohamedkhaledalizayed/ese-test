package com.neqabty.healthcare.settings

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.core.app.NotificationManagerCompat
import com.google.android.play.core.review.ReviewException
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.model.ReviewErrorCode
import com.neqabty.healthcare.R
import com.neqabty.healthcare.aboutapp.AboutAppActivity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivitySettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : BaseActivity<ActivitySettingsBinding>() {


    lateinit var reviewInfo: ReviewInfo
    override fun getViewBinding() = ActivitySettingsBinding.inflate(layoutInflater)
    private val  bottomSheetFragment: ChangePasswordBottomSheet by lazy { ChangePasswordBottomSheet() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.settings_title)

        binding.aboutContainer.setOnClickListener { startActivity(Intent(this, AboutAppActivity::class.java)) }
        binding.backBtn.setOnClickListener { finish() }
//        binding.notificationContainer.setOnClickListener { startActivity(Intent(this@SettingsActivity, NotificationsActivity::class.java)) }
//        binding.changePasswordContainer.setOnClickListener {
//            val intent = Intent(this, ChangePasswordActivity::class.java)
//            intent.putExtra("token", sharedPreferences.token)
//            startActivity(intent)
//        }
            binding.changePasswordContainer.setOnClickListener { bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag) }

        binding.rate.setOnClickListener {
            val flow = manager.launchReviewFlow(this, reviewInfo)
            flow.addOnCompleteListener { _ ->
                // The flow has finished. The API does not indicate whether the user
                // reviewed or not, or even whether the review dialog was shown. Thus, no
                // matter the result, we continue our app flow.
            }
        }
        binding.policyContainer.setOnClickListener { startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://neqabty.com/privacy-policy"))) }

        sharedPreferences.isNotificationsEnabled = NotificationManagerCompat.from(this).areNotificationsEnabled()
        binding.switchNotifications.isChecked = sharedPreferences.isNotificationsEnabled
        binding.switchNotifications.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

                val intent = Intent()
                intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"

                // for Android 5-7
                intent.putExtra("app_package", packageName)
                intent.putExtra("app_uid", applicationInfo.uid)

                // for Android 8 and above
                intent.putExtra("android.provider.extra.APP_PACKAGE", packageName)

                startActivityForResult(intent, 0)
            }
        })

    }

//    override fun onResume() {
//        super.onResume()
//        initializeViews()
//    }

//
//    private fun initializeViews() {
//        val status = NotificationManagerCompat.from(this).areNotificationsEnabled()
//        sharedPreferences.isNotificationsEnabled = status
//        binding.switchNotifications.isChecked = status
//        binding.switchNotifications.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//
//                val intent = Intent()
//                intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
//
//                // for Android 5-7
//                intent.putExtra("app_package", getPackageName())
//                intent.putExtra("app_uid", getApplicationInfo().uid)
//
//                // for Android 8 and above
//                intent.putExtra("android.provider.extra.APP_PACKAGE", getPackageName())
//
//                startActivityForResult(intent, 0)
//            }
//        })
//
//        binding.seekbar.progress = when(sharedPreferences.fontSize){
//            "small" -> 1
//            "large" -> 3
//            else -> 2
//        }
//
//        binding.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
//            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
//                when (p1) {
//                    1 -> {
//                        sharedPreferences.fontSize = "small"
//                    }
//                    3 -> {
//                        sharedPreferences.fontSize = "large"
//                    }
//                    else -> {
//                        sharedPreferences.fontSize = "medium"
//                    }
//                }
//                setTheme(getAppTheme())
//                startActivity(Intent(this@SettingsActivity, MegaHomeActivity::class.java))
//                finishAffinity()
//            }
//
//            override fun onStartTrackingTouch(p0: SeekBar?) {
//            }
//
//            override fun onStopTrackingTouch(p0: SeekBar?) {
//            }
//        })
//
//    }
//
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

    private lateinit var manager: ReviewManager
    override fun onResume() {
        super.onResume()
        manager = ReviewManagerFactory.create(this)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // We got the ReviewInfo object
                reviewInfo = task.result

            } else {
                // There was some problem, log or handle the error code.
                @ReviewErrorCode val reviewErrorCode = (task.exception as ReviewException).errorCode
            }
        }
    }


}