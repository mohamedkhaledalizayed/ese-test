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
                imageList.removeAt(id)
                updateView()
            }
        }

        updateView()
        binding.cartRecycler.adapter = mAdapter
        mAdapter.onItemClickListener = object :
            CartAdapter.OnItemClickListener {
            override fun setOnItemClickListener(id: Int) {

            }

            override fun notifyUi() {
                updateView()
            }
        }
        mAdapter.submitList(cartItems)

        binding.ivAddPhoto.setOnClickListener {
            addNewImage()
        }
    }

    private fun updateView() {
        /////Images recyclerView
        if(cartItems.isEmpty())
            binding.hsvPhotos.visibility = View.VISIBLE
        else
            binding.hsvPhotos.visibility = View.GONE

        if (imageList.isEmpty()) {
            binding.numberImage.visibility = View.GONE
            binding.view.visibility = View.GONE
        } else {
            binding.view.visibility = View.VISIBLE
//            binding.numberImage.text = " تم تحميل ${imageList.size - 1} صور"
        }
        photoAdapter.submitList(imageList)


        ///// checkout btn
        if (cartItems.isEmpty() && imageList.isEmpty()){
            binding.checkout.visibility = View.GONE
        }else{
            binding.checkout.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                val uri: Uri = data?.data!!
                imageList.add(uri)
            }
            ImagePicker.RESULT_ERROR -> {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
        updateView()
    }

    private fun addNewImage() {
        ImagePicker.with(this)
            .crop()	    			//Crop image(Optional), Check Customization for more option
            .compress(1024)			//Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
            .start()
    }

    fun checkOut(view: View) {
        startActivity(Intent(this, CheckOutActivity::class.java))
        finish()
    }

}