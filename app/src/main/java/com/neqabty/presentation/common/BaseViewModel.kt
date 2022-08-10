package com.neqabty.presentation.common

import androidx.lifecycle.ViewModel
import com.neqabty.MyApp
import com.neqabty.R
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(): ViewModel() {

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
                    return Throwable(MyApp.appResources.getString(R.string.error_msg))
                }
            } else if (exception.code() == 401)
                return Throwable(MyApp.appResources.getString(R.string.authentication_error))
            else if (exception.code() == 400)
                return Throwable(MyApp.appResources.getString(R.string.authentication_error))
            else
                return Throwable(MyApp.appResources.getString(R.string.error_msg))
        }catch(e :Exception){
//            val exception = throwable as ConnectException
            return Throwable(MyApp.appResources.getString(R.string.no_connection_msg))
        }
    }
}
