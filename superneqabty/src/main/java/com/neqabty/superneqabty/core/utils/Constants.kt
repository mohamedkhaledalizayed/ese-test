package com.neqabty.superneqabty.core.utils

import com.neqabty.superneqabty.BuildConfig
import me.cowpay.util.CowpayConstantKeys

object Constants {
    var cowpayAuthTokenProd: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjhlNzRkZWI2MDQzZDVhZjUzYTMyNTY4YmRhOWM3MmVmNGFjYmFlMjQ5YThmMzA3ZWNkZGVjOTk4ZmM5M2MyODdlODUxYTk1YzIwODk2MzU0In0.eyJhdWQiOiIzIiwianRpIjoiOGU3NGRlYjYwNDNkNWFmNTNhMzI1NjhiZGE5YzcyZWY0YWNiYWUyNDlhOGYzMDdlY2RkZWM5OThmYzkzYzI4N2U4NTFhOTVjMjA4OTYzNTQiLCJpYXQiOjE2MDczNTI1MTcsIm5iZiI6MTYwNzM1MjUxNywiZXhwIjoxNjM4ODg4NTE3LCJzdWIiOiIzODUiLCJzY29wZXMiOltdfQ.Ix26ZW685ouEDylyxOAvZkMJvWEue7BhjJugBd1LBF4Gm2rxIsG3ec3qVlZDRLneK3RzdSrUF-p4qiTU9aWOZg"
    var cowpayAuthTokenTest: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImQ1OGUwYzQxMGNhYzFiYTYzNmZlZmY5ODMwNDhkNGFjNDg3MWQ3M2Q3OTI2ZGQ4ZDFmYWYwM2IzYWY1YTYyM2Y0OWY1NWRmOWQ1MGNhYTFjIn0.eyJhdWQiOiIxIiwianRpIjoiZDU4ZTBjNDEwY2FjMWJhNjM2ZmVmZjk4MzA0OGQ0YWM0ODcxZDczZDc5MjZkZDhkMWZhZjAzYjNhZjVhNjIzZjQ5ZjU1ZGY5ZDUwY2FhMWMiLCJpYXQiOjE2MDY5MzA2MzUsIm5iZiI6MTYwNjkzMDYzNSwiZXhwIjoxNjM4NDY2NjM1LCJzdWIiOiIzODciLCJzY29wZXMiOltdfQ.PTyQdVixvfIJRcsMWdId7TrwOuGGPaNIor24XsHLExNMPcOemh1nmw6sRWgem9kb4xKB73Q3DWcRnudv44vhDw"
    var cowpayAuthToken: String = if (BuildConfig.DEBUG) cowpayAuthTokenTest else cowpayAuthTokenProd


    var CALL_CENTER = "0235317300"
    var GENERAL_NEWS = 1
    var SYNDICATE_NEWS = 2

//    var DNS = if (BuildConfig.URL.contains("http")) BuildConfig.URL else "https://${BuildConfig.URL}"

    var OPAY_PAYMENT_CALLBACK_URL = (if (BuildConfig.DEBUG) "http://backend.neqabty.com:44392" else "") + "/api/v1/transactions/opay/callback"
    var OPAY_MERCHANT_ID = if (BuildConfig.DEBUG) "281821120532113" else "281821120144533"
    var OPAY_MERCHANT_NAME = if (BuildConfig.DEBUG) "neQabty TEST" else "neQabty"
    var OPAY_PUBLIC_KEY = if (BuildConfig.DEBUG) "OPAYPUB16386946354660.48619730311207143" else "OPAYPUB16383585551500.36105494805860716"
    var COWPAY_MODE = if (BuildConfig.DEBUG) CowpayConstantKeys.SandBox else CowpayConstantKeys.Production
    var OPAY_MODE = BuildConfig.DEBUG
    var CC_COMMISSION = .0288
    var POS_COMMISSION = .0101
    var FAWRY_COMMISSION = .0101
    var MIN_COMMISSION = 6.0

    enum class PaymentOption {
        OpayCredit,
        OpayPOS,
        Fawry
    }
}
