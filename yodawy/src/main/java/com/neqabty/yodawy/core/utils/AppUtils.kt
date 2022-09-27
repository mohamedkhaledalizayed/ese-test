package com.neqabty.yodawy.core.utils

import android.annotation.SuppressLint
import retrofit2.HttpException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AppUtils {

    @SuppressLint("SimpleDateFormat")
    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    fun dateFormat(date: String): String{
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        var newDate = Date()
        return try {
            newDate = format.parse(date)
            val arabicFormat = SimpleDateFormat("dd MMM yyy - hh:mm a", Locale("ar"))

            arabicFormat.format(newDate.time)
        }catch (e: Exception){
            date
        }

    }

    fun handleError(throwable: Throwable): String {
        return if (throwable is HttpException) {
            when (throwable.code()) {
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
                    "نأسف، لقد حدث خطأ.. برجاء المحاولة في وقت لاحق"
                }
            }
        } else {
            "نأسف، لقد حدث خطأ.. برجاء المحاولة في وقت لاحق"
        }
    }
}