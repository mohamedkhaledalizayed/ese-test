package com.neqabty.presentation.common

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.util.AttributeSet
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatSpinner
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.neqabty.MyApp
import com.neqabty.R
import com.neqabty.presentation.common.SpinnerModel
import java.util.*

class MultiSelectionSpinner : AppCompatSpinner, DialogInterface.OnMultiChoiceClickListener {

    var listener: OnMultipleItemsSelectedListener? = null

    private var _items: List<SpinnerModel> = listOf()
    private var simpleAdapter: ArrayAdapter<SpinnerModel> // TODO replace with custom adaper using Model
    private var TYPE = 0 // with , separated
    private var prevSelction = BooleanArray(_items.size)
    private val mSelection: BooleanArray
        get() {
            val selections = BooleanArray(_items.size)
            for (i in _items.indices) {
                selections[i] = _items[i].isSelected
            }
            return selections
        }
    private val selectedItems: List<SpinnerModel>
        get() {
            val selection = mutableListOf<SpinnerModel>()
            for (i in _items.indices) {
                if (_items[i].isSelected) {
                    selection.add(_items[i])
                }
            }
            return selection
        }

    private var hint: String = ""

    interface OnMultipleItemsSelectedListener {
        fun selectedItems(items: List<SpinnerModel>)
    }

    constructor(context: Context) : super(context) { // TODO replace the unused
        simpleAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item)
        super.setAdapter(simpleAdapter)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        simpleAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item)
        super.setAdapter(simpleAdapter)
    }

    fun setOnItemsSelectedListener(listener: OnMultipleItemsSelectedListener) {
        this.listener = listener
    }

    fun setType(type: Int) {
        TYPE = type
    }

    override fun onClick(dialog: DialogInterface, which: Int, isChecked: Boolean) {
        if (!_items.isNullOrEmpty() && which < _items.size) {
            _items[which].isSelected = isChecked
            simpleAdapter.clear()
            simpleAdapter.add(buildSelectedItemString())
        }
    }

    override fun performClick(): Boolean {

        val dialog = MaterialAlertDialogBuilder(context, R.style.MyAlertDialogTheme)
            .setTitle(hint)
            .setBackground(resources.getDrawable(R.drawable.rounded_bg))
            .setMultiChoiceItems(_items.map { it.name }.toTypedArray(), mSelection, this)
            .setPositiveButton(context.getString(R.string.ok_btn)) { dialog, which ->
//                setSpinnerHint()
                prevSelction = mSelection
                listener!!.selectedItems(selectedItems)
            }
            .setNegativeButton(
                context.getString(R.string.cancel_btn)
            ) { _, _ ->
                setPrevSelections()
            }
            .show()

        val search = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        search.setBackgroundColor(Color.WHITE)

        val cancel = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
        cancel.setBackgroundColor(Color.WHITE)

        return true
    }

    private fun setPrevSelections() {
        val selections = LinkedList<SpinnerModel>()
        for (i in _items.indices) {
            _items[i].isSelected = prevSelction[i]
            if (_items[i].isSelected)
                selections.add(_items[i])
        }
        setSelection(selections, true)
    }

    fun setItems(items: List<SpinnerModel>) {
        _items = items
        prevSelction = BooleanArray(_items.size)
        simpleAdapter.clear()
        setSpinnerHint()

    }

    private fun setSpinnerHint() {
        simpleAdapter.clear()
        simpleAdapter.add(SpinnerModel(name = hint))
    }


    fun setHint(hint: String) {
        this.hint = hint
        setSpinnerHint()
    }

    fun setSelection(
        selection: List<SpinnerModel>,
        cancel: Boolean = false
    ) { // TODO test if remove this function
        for (i in _items.indices) {// clears all selection
            _items[i].isSelected = false
        }
        for (sel in selection) { // TODO simplify this @#@@#$%!@@#%#$%&(^*&^#%&^*
            for (j in _items.indices) {
                if (_items[j].id == sel.id) {
                    _items[j].isSelected = true
                }
            }
        }
        simpleAdapter.clear()
        simpleAdapter.add(buildSelectedItemString())
        if (!cancel)
            listener?.selectedItems(selection)
//        setSpinnerHint()
    }

    override fun setSelection(index: Int) {
        for (i in _items.indices) { // clears all selection
            _items[i].isSelected = false
        }
        if (index >= 0 && index < _items.size) { // checks selection validity to prevent crashes
            _items[index].isSelected = true
        } else {
            throw IllegalArgumentException(
                "Index " + index
                        + " is out of bounds."
            )
        }
        simpleAdapter.clear()
        simpleAdapter.add(buildSelectedItemString())
    }

    private fun buildSelectedItemString(): SpinnerModel {
        var sb = StringBuilder()
        var foundOne = false
        if (selectedItems.isNotEmpty()) {
            for (i in _items.indices) {
                if (_items[i].isSelected) {
                    if (foundOne) {
                        sb.append(", ")
                    }
                    foundOne = true

                    sb.append(_items[i].name)
                }
            }
        } else {
            sb.append(hint)
        }
        return if (TYPE != 0 && selectedItems.size > 1) {
            SpinnerModel(name = MyApp.appResources.getString(R.string.multi_selection))
        } else {
            SpinnerModel(name = sb.toString())
        }
    }

}
