package com.neqabty.yodawy.modules

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.yodawy.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {
    private var list: MutableList<Medication> = ArrayList<Medication>()
    private val mAdapter = CartAdapter()
    private lateinit var photoAdapter: PhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)


//        photoAdapter = PhotosAdapter(this)
//        findViewById<RecyclerView>(R.id.photos_recycler).adapter = photoAdapter
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.title = "Your Cart"
//        supportActionBar?.setHomeAsUpIndicator(R.drawable.back)
        findViewById<RecyclerView>(R.id.cart_recycler).adapter = mAdapter
        mAdapter.onItemClickListener = object :
            CartAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: Int) {

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
        mAdapter.submitList(list)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_note_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }else if (item.itemId == R.id.add_note){

        }
        return super.onOptionsItemSelected(item)
    }

    fun checkOut(view: View) {
        startActivity(Intent(this, CheckOutActivity::class.java))
    }

}