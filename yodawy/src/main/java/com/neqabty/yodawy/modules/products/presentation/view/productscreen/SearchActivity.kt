package com.neqabty.yodawy.modules.products.presentation.view.productscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.yodawy.R
import com.neqabty.yodawy.modules.CartActivity
import com.neqabty.yodawy.modules.Medication
import com.neqabty.yodawy.modules.address.presentation.view.adressscreen.AddressViewModel
import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity
import com.vlonjatg.progressactivity.ProgressRelativeLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private var list: MutableList<Medication> = ArrayList<Medication>()
    private val mAdapter = SearchAdapter()
    private lateinit var toolbar: Toolbar
    private val productViewModel: ProductViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        toolbar = findViewById<Toolbar>(R.id.search_custom_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.elevation = 0.0f
        findViewById<RecyclerView>(R.id.recycler_view).adapter = mAdapter

        productViewModel.search("panadol")
        productViewModel.data.observe(this){
            Log.e("gfgh", it[0].name)
            mAdapter.submitList(it)
        }
        mAdapter.onItemClickListener = object :
            SearchAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: ProductEntity) {
                val intent: Intent = Intent(this@SearchActivity, ProductDetailsActivity::class.java)
                intent.putExtra("product", item)
                startActivity(intent)
            }
        }
        toolbar.findViewById<ImageView>(R.id.back_btn).setOnClickListener { finish() }
        toolbar.findViewById<FrameLayout>(R.id.cart).setOnClickListener { startActivity(Intent(this, CartActivity::class.java)) }

//        setDate()
    }


    private fun setDate(){
        var medication = Medication("name", 1, "image")
        list.add(medication)
        medication = Medication("name", 1, "image")
        list.add(medication)
        medication = Medication("name", 2, "image")
        list.add(medication)
        medication = Medication("name", 1, "image")
        list.add(medication)
        medication = Medication("name", 1, "image")
        list.add(medication)
        medication = Medication("name", 1, "image")
        list.add(medication)
        medication = Medication("name", 2, "image")
        list.add(medication)
        medication = Medication("name", 1, "image")
        list.add(medication)
        medication = Medication("name", 1, "image")
        list.add(medication)
        medication = Medication("name", 1, "image")
        list.add(medication)
        medication = Medication("name", 3, "image")
        list.add(medication)
        medication = Medication("name", 1, "image")
        list.add(medication)
        medication = Medication("name", 1, "image")
        list.add(medication)
    }
}