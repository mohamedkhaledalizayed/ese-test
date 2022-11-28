package com.neqabty.healthcare.sustainablehealth.mypackages.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neqabty.healthcare.R
import com.neqabty.healthcare.databinding.PackageLayoutBinding
import com.neqabty.healthcare.sustainablehealth.mypackages.domain.entity.profile.SubscribedPackageEntity
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
        if (follower.packages.expiryDate != null){
            viewHolder.binding.date.text = "تاريخ الصلاحية : ${follower.packages.expiryDate}"
        }else{
            viewHolder.binding.date.text = ""
        }

        val mAdapter = FollowerAdapter()
        viewHolder.binding.followersRecycler.adapter = mAdapter

        if (follower.packages.paid || follower.packages.prepaid){
            viewHolder.binding.paid.visibility = View.GONE
        }else{
            viewHolder.binding.paid.visibility = View.VISIBLE
        }


        if (follower.packages.prepaid){
            viewHolder.binding.addFollower.visibility = View.VISIBLE
            viewHolder.binding.addFollower.setImageResource(R.drawable.ic_baseline_edit_24)
            viewHolder.binding.addFollowerText.visibility = View.GONE
            viewHolder.binding.editPackage.visibility = View.VISIBLE
        }else if (follower.packages.maxFollower == 0 && !follower.packages.prepaid){
            viewHolder.binding.addFollower.visibility = View.GONE
            viewHolder.binding.addFollowerText.visibility = View.GONE
            viewHolder.binding.editPackage.visibility = View.GONE
        }else{
            viewHolder.binding.addFollower.setImageResource(R.drawable.ic_baseline_add_circle_outline_24)
            viewHolder.binding.addFollowerText.visibility = View.VISIBLE
            viewHolder.binding.editPackage.visibility = View.GONE
        }

        mAdapter.submitList(follower.packages.followers)

        viewHolder.binding.paid.setOnClickListener {
            if (follower.packages.serviceActionCode.isNullOrEmpty()){
                return@setOnClickListener
            }
            onItemClickListener?.setOnPayClickListener(follower.packages.nameAr, follower.packages.packagePrice ?: "0", follower.packages.serviceCode, follower.packages.serviceActionCode)
        }

        mAdapter.onItemClickListener = object :
            FollowerAdapter.OnItemClickListener {
            override fun setOnItemClickListener(subscriberId: String, followerId: Int) {
                onItemClickListener?.setOnDeleteItemClickListener(subscriberId, followerId)
            }
        }

        viewHolder.binding.editPackage.setOnClickListener {
            onItemClickListener?.setOnEditClickListener()
        }

        viewHolder.binding.addFollower.setOnClickListener {
            if (follower.packages.followers.size >= follower.packages.maxFollower){
                onItemClickListener?.setOnAddItemClickListener(follower.packages.id, follower.packages.subscriberId,true)
            }else{
                onItemClickListener?.setOnAddItemClickListener(follower.packages.id, follower.packages.subscriberId, false)
            }
        }

        viewHolder.binding.addFollowerText.setOnClickListener {
            if (follower.packages.followers.size >= follower.packages.maxFollower){
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
            fun setOnPayClickListener(name: String, price: String, serviceCode: String, serviceActionCode: String)
            fun setOnEditClickListener()
    }

    class ViewHolder(val binding: PackageLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}