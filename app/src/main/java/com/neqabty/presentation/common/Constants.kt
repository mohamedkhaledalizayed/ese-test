package com.neqabty.presentation.common

import androidx.lifecycle.MutableLiveData
import com.neqabty.BuildConfig
import com.neqabty.MainViewState
import me.cowpay.util.CowpayConstantKeys

object Constants {
    var JWT: String = ""
    var cowpayAuthTokenProd: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjhlNzRkZWI2MDQzZDVhZjUzYTMyNTY4YmRhOWM3MmVmNGFjYmFlMjQ5YThmMzA3ZWNkZGVjOTk4ZmM5M2MyODdlODUxYTk1YzIwODk2MzU0In0.eyJhdWQiOiIzIiwianRpIjoiOGU3NGRlYjYwNDNkNWFmNTNhMzI1NjhiZGE5YzcyZWY0YWNiYWUyNDlhOGYzMDdlY2RkZWM5OThmYzkzYzI4N2U4NTFhOTVjMjA4OTYzNTQiLCJpYXQiOjE2MDczNTI1MTcsIm5iZiI6MTYwNzM1MjUxNywiZXhwIjoxNjM4ODg4NTE3LCJzdWIiOiIzODUiLCJzY29wZXMiOltdfQ.Ix26ZW685ouEDylyxOAvZkMJvWEue7BhjJugBd1LBF4Gm2rxIsG3ec3qVlZDRLneK3RzdSrUF-p4qiTU9aWOZg"
    var cowpayAuthTokenTest: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImQ1OGUwYzQxMGNhYzFiYTYzNmZlZmY5ODMwNDhkNGFjNDg3MWQ3M2Q3OTI2ZGQ4ZDFmYWYwM2IzYWY1YTYyM2Y0OWY1NWRmOWQ1MGNhYTFjIn0.eyJhdWQiOiIxIiwianRpIjoiZDU4ZTBjNDEwY2FjMWJhNjM2ZmVmZjk4MzA0OGQ0YWM0ODcxZDczZDc5MjZkZDhkMWZhZjAzYjNhZjVhNjIzZjQ5ZjU1ZGY5ZDUwY2FhMWMiLCJpYXQiOjE2MDY5MzA2MzUsIm5iZiI6MTYwNjkzMDYzNSwiZXhwIjoxNjM4NDY2NjM1LCJzdWIiOiIzODciLCJzY29wZXMiOltdfQ.PTyQdVixvfIJRcsMWdId7TrwOuGGPaNIor24XsHLExNMPcOemh1nmw6sRWgem9kb4xKB73Q3DWcRnudv44vhDw"
    var cowpayAuthToken: String = if(BuildConfig.DEBUG) cowpayAuthTokenTest else cowpayAuthTokenProd
    var isFirebaseTokenUpdated: MutableLiveData<Boolean> = MutableLiveData(false)

    var CALL_CENTER = "0235317300"

    var IP = "http://3.131.229.146:44392/"
    var PROD_IP = "front.neqabty.com"
    var DNS = if(BuildConfig.DEBUG) IP else "http://$PROD_IP"

    var COWPAY_MODE = if(BuildConfig.DEBUG) CowpayConstantKeys.SandBox else CowpayConstantKeys.Production
    var CLAIMING = 1
    var TRIPS = 2
    var RECORDS = 3
    var UPDATE_DATA = 4
    var COMPLAINTS = 5
    var CORONA = 6
    var MEDICAL_RENEW = 7


    var DELIVERY_LOCATION_SYNDICATE = 1
    var DELIVERY_LOCATION_HOME = 2
    var DELIVERY_LOCATION_MAIN_SYNDICATE = 3

    var PAYMENT_TYPE_TRIPS = "9991"
    var PAYMENT_TYPE_RECORDS = "9992"
    var PAYMENT_TYPE_MEDICAL_RENEW = "9993"
}