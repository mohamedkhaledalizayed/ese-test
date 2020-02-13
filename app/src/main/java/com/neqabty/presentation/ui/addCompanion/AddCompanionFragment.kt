package com.neqabty.presentation.ui.addCompanion

import android.app.Activity
import android.app.DatePickerDialog
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.content.res.Resources
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.neqabty.AppExecutors
import com.neqabty.R
import com.neqabty.databinding.AddCompanionFragmentBinding
import com.neqabty.domain.entities.PersonEntity
import com.neqabty.presentation.binding.FragmentDataBindingComponent
import com.neqabty.presentation.di.Injectable
import com.neqabty.presentation.util.autoCleared
import kotlinx.android.synthetic.main.add_companion_fragment.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


class AddCompanionFragment : DialogFragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    var binding by autoCleared<AddCompanionFragmentBinding>()
    @Inject
    lateinit var appExecutors: AppExecutors

    val myCalendar = Calendar.getInstance()
    var relationsList: MutableList<String>? = mutableListOf("مهندس/عضو", "زوجة", "ابن", "ابنة", "والد", "والدة", "طفل")
    var selectedRelation = ""


    var companion = PersonEntity()
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.add_companion_fragment,
                container,
                false,
                dataBindingComponent
        )

//        this.dialog.window.setLayout(DisplayMetrics.width /2 , ViewGroup.LayoutParams.WRAP_CONTENT)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeViews()
    }

    fun initializeViews() {
        var layoutParams = binding.clRoot.layoutParams!!
        layoutParams.width = Resources.getSystem().displayMetrics.widthPixels * 8 / 10
        binding.clRoot.layoutParams = layoutParams

        renderRelations()
        updateLabel()

        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel()
        }, year, month, day)

        edBirthDate.setOnClickListener {
            datePicker.show()
        }

        bOK.setOnClickListener {
            if (edAgeOnTrip.text.toString().length == 0) {
                Toast.makeText(context, getString(R.string.invalid_data), Toast.LENGTH_LONG).show()
            } else {
                companion.name = ""
                companion.relationship = selectedRelation
                companion.ageOnTrip = edAgeOnTrip.text.toString().toInt()
                companion.birthDate = edBirthDate.text.toString()

                val intent = Intent()
                val bundle = Bundle()
                bundle.putParcelable("companion", companion)
                intent.putExtras(bundle)
                targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
                dialog.dismiss()
            }
        }

        bCancel.setOnClickListener { dialog.dismiss() }
    }


    //region
    private fun updateLabel() {
        val myFormat = "MM/dd/yy"; //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        edBirthDate.setText(sdf.format(myCalendar.getTime()))
    }


    fun renderRelations() {
        binding.spRelationDegree.adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, relationsList)
        binding.spRelationDegree.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedRelation = (parent.getItemAtPosition(position) as String)
            }
        }
        binding.spRelationDegree.setSelection(0)
    }
// endregion
}