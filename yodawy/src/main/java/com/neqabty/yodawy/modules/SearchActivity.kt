package com.neqabty.yodawy.modules

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.yodawy.R

class SearchActivity : AppCompatActivity() {
    private var list: MutableList<Medication> = ArrayList<Medication>()
    private val mAdapter = SearchAdapter()
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        toolbar = findViewById<Toolbar>(R.id.search_custom_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.elevation = 0.0f
        findViewById<RecyclerView>(R.id.recycler_view).adapter = mAdapter
        mAdapter.onItemClickListener = object :
            SearchAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: Int) {
                startActivity(Intent(this@SearchActivity, ProductDetailsActivity::class.java))
            }
        }
        setDate()
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
        mAdapter.submitList(list)
    }
}