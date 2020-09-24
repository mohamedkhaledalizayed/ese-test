package com.neqabty.presentation.ui.common

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.neqabty.R

class CustomImagePagerAdapter(val context: Context, val imgs: List<String>) : PagerAdapter() {
    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p1 == p0
    }

    override fun getCount(): Int {
        return imgs.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.image_pager_item, null)
        val imageView = view.findViewById<ImageView>(R.id.ivLogo)
        Glide.with(context).load(Uri.parse(imgs[position])).into(imageView)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}