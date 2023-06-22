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

        val mInsuranceAdapter = InsuranceAdapter()
        viewHolder.binding.insuranceRecycler.adapter = mInsuranceAdapter

        if (follower.packages.prepaid){
            viewHolder.binding.paid.visibility = View.GONE
        }else if (follower.packages.paid){
            viewHolder.binding.paid.setBackgroundResource(R.drawable.package_paid_bg)
            viewHolder.binding.paid.text = "مدفوع"
        }else{
            viewHolder.binding.paid.setBackgroundResource(R.drawable.package_not_paid_bg)
            viewHolder.binding.paid.text = "لم يتم الدفع"
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
        mInsuranceAdapter.submitList(follower.packages.insuranceDocs)

        viewHolder.binding.paid.setOnClickListener {
            if (follower.packages.serviceActionCode.isNullOrEmpty()){
                return@setOnClickListener
            }
            if (follower.packages.paid){
                return@setOnClickListener
            }
            onItemClickListener?.setOnPayClickListener(follower)
        }

        mAdapter.onItemClickListener = object :
            FollowerAdapter.OnItemClickListener {
            override fun setOnItemClickListener(subscriberId: String, followerId: Int) {
                onItemClickListener?.setOnDeleteItemClickListener(subscriberId, followerId)
            }
        }

        mInsuranceAdapter.onItemClickListener = object :
            InsuranceAdapter.OnItemClickListener {
            override fun setOnItemClickListener(item: String) {
                onItemClickListener?.setOnDownloadClickListener(item)
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
            fun setOnPayClickListener(item: SubscribedPackageEntity)
            fun setOnDownloadClickListener(item: String)
            fun setOnEditClickListener()
    }

    class ViewHolder(val binding: PackageLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}