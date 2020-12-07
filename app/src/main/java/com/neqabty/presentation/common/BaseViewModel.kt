package com.neqabty.presentation.common

import androidx.lifecycle.ViewModel
import com.neqabty.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException

open class BaseViewModel : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private fun clearDisposables() {
        compositeDisposable.clear()
    }

    override fun onCleared() {
        clearDisposables()
    }

    protected fun handleError(throwable: Throwable): Throwable {
        try {
            val exception = throwable as HttpException

            if (exception.code() == 406) {
                try {
                    val errorObject = JSONObject(exception.response()!!.errorBody()?.string())
                    return Throwable(errorObject.getString("status_message_ar"))
                } catch (e: Exception) {
                    return Throwable("حدث خطأ بالإتصال")
                }
            } else if (exception.code() == 401)
                return Throwable("لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى")
            else if (exception.code() == 400)
                return Throwable("لقد تم تسجيل الدخول من قبل برجاء تسجيل الخروج واعادة المحاولة مرة اخرى")
            else
                return Throwable("نأسف لحدوث خطأ بالإتصال")
        }catch(e :Exception){
//            val exception = throwable as ConnectException
                return Throwable("من فضلك تحقق من الإتصال بالإنترنت وحاول مجدداً")
        }
    }
}
