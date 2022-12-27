package com.neqabty.chefaa.core.utils

import android.view.*
import android.widget.EditText

fun String.replaceText(): String{
    return this.replace("\\", "/")
}

fun ViewGroup.forAllChildren(forOneChild: (v: View) -> Unit) {
    forOneChild(this)
    for (cx in 0 until childCount) {
        val child = getChildAt(cx)
        if (child is ViewGroup)
            child.forAllChildren(forOneChild)
        else
            forOneChild(child)
    }
}

fun EditText.disableCopying() {
    customSelectionActionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
            return false
        }

        override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
            return false
        }

        override fun onDestroyActionMode(p0: ActionMode?) {

        }
    }
}