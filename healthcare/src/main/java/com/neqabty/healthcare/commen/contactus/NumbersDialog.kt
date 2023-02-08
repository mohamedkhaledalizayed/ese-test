package com.neqabty.healthcare.commen.contactus

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.neqabty.healthcare.R


class NumbersDialog : DialogFragment() {

    private var numbers = ""
    private lateinit var mAdapter: NumbersAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            numbers = it.getString(NUMBERS)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                mAdapter = NumbersAdapter(numbers.split("-"))
                adapter = mAdapter
            }
        }


        mAdapter.onItemClickListener = object :NumbersAdapter.OnItemClickListener{
            override fun setOnItemClickListener(item: String) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$item")
                try {
                    startActivity(intent)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }

        }
        return view
    }



    companion object {

        const val NUMBERS = "numbers"

        @JvmStatic
        fun newInstance(numbers: String) =
            NumbersDialog().apply {
                arguments = Bundle().apply {
                    putString(NUMBERS, numbers)
                }
            }
    }
}