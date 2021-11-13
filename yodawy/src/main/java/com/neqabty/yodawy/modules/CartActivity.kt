package com.neqabty.yodawy.modules

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.databinding.ActivityCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : BaseActivity<ActivityCartBinding>() {

    override fun getViewBinding() = ActivityCartBinding.inflate(layoutInflater)
    private var list: MutableList<Medication> = ArrayList<Medication>()
    private val mAdapter = CartAdapter()
    private lateinit var photoAdapter: PhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.cart)


//        photoAdapter = PhotosAdapter(this)
        findViewById<RecyclerView>(R.id.cart_recycler).adapter = mAdapter
        mAdapter.onItemClickListener = object :
            CartAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: Int) {

            }
        }
        setDate()
    }

    private fun setDate(){
//        var medication = Medication("name", 1, "image", 1)
//        list.add(medication)
//        medication = Medication("name", 1, "image", 1)
//        list.add(medication)
//        medication = Medication("name", 2, "image", 1)
//        list.add(medication)
        mAdapter.submitList(Constants.cartItems)
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.add_note_menu,menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == android.R.id.home) {
//            finish()
//        }else if (item.itemId == R.id.add_note){
//
//        }
//        return super.onOptionsItemSelected(item)
//    }

    fun checkOut(view: View) {
        startActivity(Intent(this, CheckOutActivity::class.java))
    }

}