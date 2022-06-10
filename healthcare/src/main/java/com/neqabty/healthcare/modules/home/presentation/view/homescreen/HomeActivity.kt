package com.neqabty.healthcare.modules.home.presentation.view.homescreen




import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.core.ui.BaseActivity
import com.neqabty.healthcare.databinding.ActivityHomeBinding
import com.neqabty.healthcare.modules.home.presentation.view.about.AboutFragment
import com.neqabty.healthcare.modules.search.presentation.view.search.SearchActivity
import com.neqabty.healthcare.modules.wallet.presentation.WalletActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(), NavigationView.OnNavigationItemSelectedListener {

    private val mAdapter = OurNewsAdapter()
    private lateinit var toolbar: Toolbar
    private lateinit var drawer: DrawerLayout
    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupToolbar(title = "الرئيسية")
        toolbar = binding.homeContent.customToolbar.toolbar

        drawer = binding.drawerLayout

        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer.addDrawerListener(toggle)
        toggle.syncState()
        toolbar.setNavigationIcon(R.drawable.ic_menu)
        binding.navView.setNavigationItemSelectedListener(this)
        binding.homeContent.ourNewsRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            OurNewsAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: String) {

            }
        }

        mAdapter.submitList(mutableListOf())

        binding.homeContent.startNow.setOnClickListener { startActivity(Intent(this, SearchActivity::class.java)) }

        binding.homeContent.aboutSehaNeqbty.setOnClickListener { aboutDetails() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.notification_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }


    private fun aboutDetails() {
        val fm: FragmentManager = supportFragmentManager
        val dialog = AboutFragment()
        dialog.show(fm, "")
        dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.wallet -> {
                val intent = Intent(this@HomeActivity, WalletActivity::class.java)
                startActivity(intent)
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}