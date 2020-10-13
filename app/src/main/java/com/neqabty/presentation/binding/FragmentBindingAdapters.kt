package com.neqabty.presentation.binding

import androidx.databinding.BindingAdapter
import android.net.Uri
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import javax.inject.Inject

/**
 * Binding adapters that work with a fragment instance.
 */

class FragmentBindingAdapters @Inject constructor(val fragment: Fragment) {
    @BindingAdapter("imageUrl")
    fun bindImageURL(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(fragment).load(Uri.parse(url)).into(imageView)
        }
    }
    @BindingAdapter("srcImage")
    fun bindImageSrc(imageView: ImageView, src: Int?) {
        src?.let {
            Glide.with(fragment).load(src).into(imageView)
        }
    }
}
