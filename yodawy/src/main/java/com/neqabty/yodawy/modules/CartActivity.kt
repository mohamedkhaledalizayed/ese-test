package com.neqabty.yodawy.modules

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.imagepicker.ImagePicker
import com.neqabty.yodawy.R
import com.neqabty.yodawy.core.data.Constants
import com.neqabty.yodawy.core.data.Constants.cartItems
import com.neqabty.yodawy.core.data.Constants.imageList
import com.neqabty.yodawy.core.ui.BaseActivity
import com.neqabty.yodawy.databinding.ActivityCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : BaseActivity<ActivityCartBinding>() {

    override fun getViewBinding() = ActivityCartBinding.inflate(layoutInflater)
    private val mAdapter = CartAdapter()
    private lateinit var photoAdapter: PhotosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupToolbar(titleResId = R.string.cart)

        photoAdapter = PhotosAdapter(this)
        binding.photosRecycler.adapter = photoAdapter
        photoAdapter.onItemClickListener = object :
            PhotosAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: Int) {
                if (id == 0) {
                    addNewImage()
                } else {
                    imageList.removeAt(id)
                    if (imageList.size == 1){
                        imageList.clear()
                        binding.checkout.visibility = View.GONE
                        binding.deliveryTime.visibility = View.GONE
                        binding.pricesPolicy.visibility = View.GONE
                        checkImages()
                    }
                    photoAdapter.clear()
                    photoAdapter.submitList(imageList)
                }
            }
        }

        checkImages()

        if (cartItems.isNotEmpty() || imageList.isNotEmpty()){
            binding.checkout.visibility = View.VISIBLE
        }else{
            binding.checkout.visibility = View.GONE
            binding.deliveryTime.visibility = View.GONE
            binding.pricesPolicy.visibility = View.GONE
        }

        binding.cartRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            CartAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: Int) {

            }

            override fun notifyUi() {
                if (cartItems.isEmpty()){
                    binding.checkout.visibility = View.GONE
                    binding.deliveryTime.visibility = View.GONE
                    binding.pricesPolicy.visibility = View.GONE
                }
            }
        }
        mAdapter.submitList(cartItems)
    }

    private fun checkImages() {
        if (imageList.isEmpty()) {
            binding.numberImage.visibility = View.GONE
            binding.photosRecycler.visibility = View.GONE
            binding.view.visibility = View.GONE
        } else {
            photoAdapter.submitList(imageList)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val uri: Uri = data?.data!!
                if (imageList.isEmpty()){
                    imageList.add(0, Uri.EMPTY)
                }
                imageList.add(uri)
                photoAdapter.clear()
                photoAdapter.submitList(imageList)
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addNewImage() {
        ImagePicker.with(this)
            .crop()	    			//Crop image(Optional), Check Customization for more option
            .compress(1024)			//Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
            .start()
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
        finish()
    }

}