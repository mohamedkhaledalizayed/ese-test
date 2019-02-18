package com.neqabty.presentation.binding

import android.databinding.BindingAdapter
import android.net.Uri
import android.support.v4.app.Fragment
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.neqabty.testing.OpenForTesting
import javax.inject.Inject

/**
 * Binding adapters that work with a fragment instance.
 */
@OpenForTesting
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
