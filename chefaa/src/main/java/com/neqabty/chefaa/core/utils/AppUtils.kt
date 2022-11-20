package com.neqabty.chefaa.core.utils

import android.annotation.SuppressLint
import org.json.JSONObject
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.*

class AppUtils {

    @SuppressLint("SimpleDateFormat")
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun dateFormat(date: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val newDate: Date = format.parse(date)
        val arabicFormat = SimpleDateFormat("dd MMM yyy - hh:mm a", Locale("ar"))

        return arabicFormat.format(newDate.time)
    }

    fun handleError(throwable: Throwable): String {
        return if (throwable is HttpException) {
            when (throwable.code()) {
                406 -> {
                    val errorObject = JSONObject(throwable.response()!!.errorBody()?.string())
                    errorObject.getString("message")
                }
                400 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                401 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                403 -> {
                    "لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى"
                }
                404 -> {
                    "نأسف، لقد حدث خطأ.. برجاء المحاولة في وقت لاحق"
                }
                500 -> {
                    "نأسف، لقد حدث خطأ.. برجاء المحاولة في وقت لاحق"
                }
                else -> {
                    try {
                        val errorObject = JSONObject(throwable.response()!!.errorBody()?.string())
                        errorObject.getString("message")
                    } catch (e: java.lang.Exception) {
                        throwable.message!!
                    }
                }
            }
        } else if (throwable is NullPointerException)
            "لا توجد بيانات"
        else
            throwable.message!!
    }
}