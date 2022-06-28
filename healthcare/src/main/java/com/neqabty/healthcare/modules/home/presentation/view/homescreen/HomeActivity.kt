package com.neqabty.healthcare.modules.home.presentation.view.homescreen




import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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
import com.neqabty.healthcare.core.utils.Status
import com.neqabty.healthcare.databinding.ActivityHomeBinding
import com.neqabty.healthcare.modules.home.presentation.view.about.AboutFragment
import com.neqabty.healthcare.modules.search.presentation.view.search.SearchActivity
import com.neqabty.healthcare.modules.wallet.presentation.WalletActivity
import com.neqabty.news.modules.home.presentation.view.newsdetails.NewsDetailsActivity
import com.neqabty.news.modules.home.presentation.view.newslist.NewsListActivity
import com.neqabty.meganeqabty.home.domain.entity.NewsEntity
import com.neqabty.meganeqabty.syndicates.presentation.view.homescreen.SyndicateActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(), NavigationView.OnNavigationItemSelectedListener {

    private val mAdapter = OurNewsAdapter()
    private val aboutAdapter = AboutAdapter()
    private lateinit var toolbar: Toolbar
    private lateinit var drawer: DrawerLayout
    override fun getViewBinding() = ActivityHomeBinding.inflate(layoutInflater)
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

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
            override fun setOnItemClickListener(item: NewsEntity) {
                val intent = Intent(this@HomeActivity, NewsDetailsActivity::class.java)
                intent.putExtra("id", item.id)
                startActivity(intent)
            }
        }

        //Get General News
        homeViewModel.getAllNews()
        homeViewModel.allNews.observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.LOADING -> {
//                        loading.show()
                    }
                    Status.SUCCESS -> {
//                        loading.dismiss()
                        if (resource.data!!.isEmpty()){
                            binding.homeContent.newsContainer.visibility = View.GONE
                            binding.homeContent.ourNewsRecycler.visibility = View.GONE
                        }else{
                            mAdapter.submitList(resource.data)
                        }
                    }
                    Status.ERROR -> {
//                        loading.dismiss()
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }
            }

        }

        binding.homeContent.aboutRecycler.adapter = aboutAdapter
        aboutAdapter.onItemClickListener = object :
            AboutAdapter.OnItemClickListener {
            override fun setOnItemClickListener(title: String, content: String) {
                aboutDetails(title, content)
            }
        }

        binding.homeContent.newsContainer.setOnClickListener {
            val intent = Intent(this@HomeActivity, NewsListActivity::class.java)
            intent.putExtra("type", 1)
            startActivity(intent)
        }

        binding.homeContent.startNow.setOnClickListener { startActivity(Intent(this, SearchActivity::class.java)) }

        homeViewModel.getAboutList()
        homeViewModel.aboutList.observe(this){

            it.let { resource ->
                when(resource.status){
                    Status.LOADING ->{
                        binding.homeContent.progressCircular.visibility = View.VISIBLE
                    }
                    Status.SUCCESS ->{
                        binding.homeContent.progressCircular.visibility = View.GONE
                        aboutAdapter.submitList(resource.data)
                    }
                    Status.ERROR ->{
                        binding.homeContent.progressCircular.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.notification_menu, menu)
        return true
    }

    private fun aboutDetails(title: String, content: String) {
        val fm: FragmentManager = supportFragmentManager
        val dialog = AboutFragment.newInstance(title, content)
        dialog.show(fm, "")
        dialog.setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.wallet -> {
                val intent = Intent(this@HomeActivity, WalletActivity::class.java)
                startActivity(intent)
            }
            R.id.syndicate -> {
                val intent = Intent(this@HomeActivity, SyndicateActivity::class.java)
                startActivity(intent)
            }
        }

        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}