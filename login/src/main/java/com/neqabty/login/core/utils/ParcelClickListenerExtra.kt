package com.neqabty.login.core.utils

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


class ParcelClickListenerExtra(val clickAction: () -> Unit) : Serializable {
    fun onClick() = clickAction()
}

//interface SerializableLambda : Function<String, String>, Serializable {}

