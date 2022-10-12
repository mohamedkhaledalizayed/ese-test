package com.neqabty.healthcare.modules.profile.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.core.data.Constants
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.FollowerItemLayoutBinding
import com.neqabty.healthcare.databinding.PackageItemLayoutBinding
import com.neqabty.healthcare.databinding.PackageLayoutBinding
import com.neqabty.healthcare.modules.home.presentation.view.homescreen.HomeActivity
import com.neqabty.healthcare.modules.profile.domain.entity.profile.FollowerEntity
import com.neqabty.healthcare.modules.profile.domain.entity.profile.SubscribedPackageEntity
import com.neqabty.healthcare.modules.syndicates.domain.entity.SyndicateEntity
import com.neqabty.healthcare.modules.syndicates.presentation.view.homescreen.SyndicateAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class PackagesAdapter: RecyclerView.Adapter<PackagesAdapter.ViewHolder>() {

    private val items: MutableList<SubscribedPackageEntity> = ArrayList()
    private var layoutInflater: LayoutInflater? = null

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {

        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        val binding: PackageLayoutBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.package_layout, parent, false)

        return ViewHolder(
            binding
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val follower = items[position]

        viewHolder.binding.packageName.text = "أنت مشترك علي ${follower.packages.nameAr}"
        viewHolder.binding.selectedPackage.text = follower.packages.nameAr
        viewHolder.binding.date.text = dateFormat(follower.packages.createdAt.split(".")[0])
        val mAdapter = FollowerAdapter()
        viewHolder.binding.followersRecycler.adapter = mAdapter

        mAdapter.submitList(follower.packages.followers)

        mAdapter.onItemClickListener = object :
            FollowerAdapter.OnItemClickListener {
            override fun setOnItemClickListener(subscriberId: String, followerId: Int) {
                onItemClickListener?.setOnDeleteItemClickListener(subscriberId, followerId)
            }
        }

        viewHolder.binding.addFollower.setOnClickListener {
            if (follower.packages.maxFollower == itemCount){
                onItemClickListener?.setOnAddItemClickListener(follower.packages.id, follower.packages.subscriberId, true)
            }else{
                onItemClickListener?.setOnAddItemClickListener(follower.packages.id, follower.packages.subscriberId, false)
            }
        }

        viewHolder.binding.addFollowerText.setOnClickListener {
            if (follower.packages.maxFollower == follower.packages.followers.size){
                onItemClickListener?.setOnAddItemClickListener(follower.packages.id, follower.packages.subscriberId,true)
            }else{
                onItemClickListener?.setOnAddItemClickListener(follower.packages.id, follower.packages.subscriberId, false)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun dateFormat(date: String): String{
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val newDate: Date = format.parse(date)
        val arabicFormat = SimpleDateFormat("dd MMM yyy - hh:mm a", Locale("ar"))

        return arabicFormat.format(newDate.time)
    }

    override fun getItemCount() = items.size

    fun submitList(newItems: List<SubscribedPackageEntity>) {
        clear()
        newItems.let {
            items.addAll(it)
            notifyDataSetChanged()
        }
    }

    @Suppress("unused")
    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
            fun setOnDeleteItemClickListener(subscriberId: String, followerId: Int)
            fun setOnAddItemClickListener(packageId: String, subscriberId: String, IsMaxFollower: Boolean)
    }

    class ViewHolder(val binding: PackageLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}