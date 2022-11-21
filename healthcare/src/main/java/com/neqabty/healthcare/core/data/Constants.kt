package com.neqabty.healthcare.core.data

import com.neqabty.healthcare.BuildConfig


object Constants {


    //Seha
    const val BASE_URL_DEV = "http://3.131.229.146:7777/api/v1/"
    const val BASE_URL_PRO = "https://seha.neqabty.com/public/api/v1/"

    //Syndicates
    const val BASE_URL_DEV_SYNDICATE = "https://neqabty.et3.co/api/"
    private const val BASE_URL_STAGING_SYNDICATE = "https://staging-community.neqabty.com/api/"
    private const val BASE_URL_PRO_SYNDICATE = "https://community.neqabty.com/api/"

    //News
    const val BASE_URL_DEV_NEWS = "https://news.et3.co/api/"
    const val BASE_URL_STAGING_NEWS = "https://staging-news.neqabty.com/api/"
    const val BASE_URL_PRO_NEWS = "https://news.neqabty.com/api//"

    //OTP
    const val BASE_URL_DEV_OTP = "https://neqabty.et3.co/"
    const val BASE_URL_STAGING_OTP = "https://staging-community.neqabty.com/"
    const val BASE_URL_PRO_OTP = "https://community.neqabty.com/"

    //Main
    const val BASE_URL_Main = BASE_URL_DEV_SYNDICATE
    const val SANDBOX = false

    const val NEQABTY_CODE = "e00"
    const val ESE_CODE = "e03"
    const val TOGAREEN_CODE = "e06"
    var isSyndicateMember = false
    var selectedSyndicateCode = ""
    var selectedSyndicatePosition = 0

    //Tour Guide
    const val TOUR_GUIDE_SYNDICATE_EMAIL = "gts@neqabty.com"
    const val TOUR_GUIDE_SYNDICATE_LAT = 0.0
    const val TOUR_GUIDE_SYNDICATE_LONG = 0.0
    const val TOUR_GUIDE_SYNDICATE_PHONE = "25899882"
    const val TOUR_GUIDE_SYNDICATE_ADDRESS = ""

    //Neqabty
    const val NEQABTY_EMAIL = "medical@neqabty.com"
    const val NEQABTY_LAT = 30.0413513
    const val NEQABTY_LONG = 31.2005282
    const val NEQABTY_PHONE = "٠٢٣٥٣٥٦٨٦٨"
    const val NEQABTY_ADDRESS = "٥٤ محي الدين ابو العز الدقي الجيزة"











    var cowpayAuthTokenProd: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjhlNzRkZWI2MDQzZDVhZjUzYTMyNTY4YmRhOWM3MmVmNGFjYmFlMjQ5YThmMzA3ZWNkZGVjOTk4ZmM5M2MyODdlODUxYTk1YzIwODk2MzU0In0.eyJhdWQiOiIzIiwianRpIjoiOGU3NGRlYjYwNDNkNWFmNTNhMzI1NjhiZGE5YzcyZWY0YWNiYWUyNDlhOGYzMDdlY2RkZWM5OThmYzkzYzI4N2U4NTFhOTVjMjA4OTYzNTQiLCJpYXQiOjE2MDczNTI1MTcsIm5iZiI6MTYwNzM1MjUxNywiZXhwIjoxNjM4ODg4NTE3LCJzdWIiOiIzODUiLCJzY29wZXMiOltdfQ.Ix26ZW685ouEDylyxOAvZkMJvWEue7BhjJugBd1LBF4Gm2rxIsG3ec3qVlZDRLneK3RzdSrUF-p4qiTU9aWOZg"
    var cowpayAuthTokenTest: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImQ1OGUwYzQxMGNhYzFiYTYzNmZlZmY5ODMwNDhkNGFjNDg3MWQ3M2Q3OTI2ZGQ4ZDFmYWYwM2IzYWY1YTYyM2Y0OWY1NWRmOWQ1MGNhYTFjIn0.eyJhdWQiOiIxIiwianRpIjoiZDU4ZTBjNDEwY2FjMWJhNjM2ZmVmZjk4MzA0OGQ0YWM0ODcxZDczZDc5MjZkZDhkMWZhZjAzYjNhZjVhNjIzZjQ5ZjU1ZGY5ZDUwY2FhMWMiLCJpYXQiOjE2MDY5MzA2MzUsIm5iZiI6MTYwNjkzMDYzNSwiZXhwIjoxNjM4NDY2NjM1LCJzdWIiOiIzODciLCJzY29wZXMiOltdfQ.PTyQdVixvfIJRcsMWdId7TrwOuGGPaNIor24XsHLExNMPcOemh1nmw6sRWgem9kb4xKB73Q3DWcRnudv44vhDw"
    var cowpayAuthToken: String = if (BuildConfig.DEBUG) cowpayAuthTokenTest else cowpayAuthTokenProd


    var CALL_CENTER = "0237376868"
    var GENERAL_NEWS = 1
    var SYNDICATE_NEWS = 2

//    var DNS = if (BuildConfig.URL.contains("http")) BuildConfig.URL else "https://${BuildConfig.URL}"

    var OPAY_PAYMENT_CALLBACK_URL = (if (BuildConfig.DEBUG) "http://backend.neqabty.com:44392" else "") + "/api/v1/transactions/opay/callback"
    var OPAY_MERCHANT_ID = if (BuildConfig.DEBUG) "281821120532113" else "281821120144533"
    var OPAY_MERCHANT_NAME = if (BuildConfig.DEBUG) "neQabty TEST" else "neQabty"
    var OPAY_PUBLIC_KEY = if (BuildConfig.DEBUG) "OPAYPUB16386946354660.48619730311207143" else "OPAYPUB16383585551500.36105494805860716"
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
