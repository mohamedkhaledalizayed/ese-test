package com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.view.search


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.neqabty.healthcare.core.data.Constants
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivitySearchBinding
import com.neqabty.healthcare.sustainablehealth.offers.presentation.view.offers.OffersActivity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.packages.PackagesEntity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.view.filter.FiltersViewModel
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.presentation.view.searchresult.SearchResultActivity
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import com.neqabty.chefaa.modules.home.presentation.homescreen.ChefaaHomeActivity
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.R
import com.neqabty.healthcare.auth.signup.presentation.view.SignupActivity
import com.neqabty.healthcare.sustainablehealth.home.presentation.view.homescreen.PackagesAdapter
import com.neqabty.healthcare.sustainablehealth.subscribtions.presentation.view.SubscriptionActivity


@AndroidEntryPoint
class SearchActivity : BaseActivity<ActivitySearchBinding>() {


    private val filtersViewModel: FiltersViewModel by viewModels()
    private val mAdapter = PackagesAdapter()
    override fun getViewBinding() = ActivitySearchBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(titleResId = R.string.seha)

        binding.packagesRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            PackagesAdapter.OnItemClickListener {
            override fun setOnRegisterClickListener(item: PackagesEntity) {

                if (item.serviceActionCode == null){
                    Toast.makeText(this@SearchActivity, "حدث خطا", Toast.LENGTH_LONG).show()
                    return
                }
                if (sharedPreferences.isAuthenticated){
                    val intent = Intent(this@SearchActivity, SubscriptionActivity::class.java)
                    intent.putExtra("name", item.name )
                    intent.putExtra("price", item.price )
                    intent.putExtra("serviceCode", item.serviceCode )
                    intent.putExtra("maxFollowers", item.maxFollower )
                    intent.putExtra("serviceActionCode", item.serviceActionCode )
                    startActivity(intent)
                }else{
                    askForLogin("عفوا هذا الرقم غير مسجل من قبل، برجاء تسجيل الدخول.")
                }
//                comingSoon("سوف يتم توفير هذه الخدمة قريبا.")
            }

            override fun setOnMoreClickListener(title: String, content: String, code: String) {
                TODO("Not yet implemented")
            }
        }

        if (sharedPreferences.code.isNullOrEmpty()){
            filtersViewModel.getPackages(Constants.NEQABTY_CODE)
        }else{
            filtersViewModel.getPackages(sharedPreferences.code)
        }
        filtersViewModel.packages.observe(this) {
            it.let { resource ->

                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE
                        mAdapter.submitList(resource.data?.toMutableList())
                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                    }
                }

            }
        }

        binding.offersContainer.setOnClickListener { startActivity(Intent(this, OffersActivity::class.java)) }

        binding.search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if(binding.search.text.toString().isNotEmpty()){
                        startActivity(Intent(this@SearchActivity, SearchResultActivity::class.java)
                            .putExtra("name", binding.search.text.toString()))
                        return true
                    }else{
                        Toast.makeText(this@SearchActivity, "من فضلك ادخل كلمة البحث", Toast.LENGTH_LONG).show()
                    }
                }
                return false
            }
        })


        binding.cvChefaa.setOnClickListener {
            val intent = Intent(this, ChefaaHomeActivity::class.java)
            intent.putExtra("user_number", sharedPreferences.mobile)
            intent.putExtra("mobile_number", sharedPreferences.mobile)
            intent.putExtra("country_code", sharedPreferences.mobile.substring(0,2))
            intent.putExtra("national_id", sharedPreferences.nationalId)
            intent.putExtra("name", sharedPreferences.name)
            intent.putExtra("jwt", "")
            startActivity(intent)
        }
    }

    private fun askForLogin(message: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("تنبيه")
        alertDialog.setMessage(message)
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, "موافق"
        ) { dialog, _ ->
            dialog.dismiss()
            Constants.isSyndicateMember = false
            Constants.selectedSyndicateCode = ""
            Constants.selectedSyndicatePosition = 0
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        alertDialog.setButton(
            AlertDialog.BUTTON_NEGATIVE, "لا"
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()

    }

    private fun comingSoon(message: String) {

        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("تنبيه")
        alertDialog.setMessage(message)
        alertDialog.setCancelable(true)
        alertDialog.setButton(
            AlertDialog.BUTTON_POSITIVE, "موافق"
        ) { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()

    }

}