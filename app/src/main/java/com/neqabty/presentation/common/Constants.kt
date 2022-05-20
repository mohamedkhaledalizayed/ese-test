package com.neqabty.presentation.common

import androidx.lifecycle.MutableLiveData
import com.neqabty.BuildConfig
import com.neqabty.presentation.entities.AdUI
import com.neqabty.presentation.entities.AppConfigUI
import me.cowpay.util.CowpayConstantKeys

object Constants {
    var cowpayAuthTokenProd: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjhlNzRkZWI2MDQzZDVhZjUzYTMyNTY4YmRhOWM3MmVmNGFjYmFlMjQ5YThmMzA3ZWNkZGVjOTk4ZmM5M2MyODdlODUxYTk1YzIwODk2MzU0In0.eyJhdWQiOiIzIiwianRpIjoiOGU3NGRlYjYwNDNkNWFmNTNhMzI1NjhiZGE5YzcyZWY0YWNiYWUyNDlhOGYzMDdlY2RkZWM5OThmYzkzYzI4N2U4NTFhOTVjMjA4OTYzNTQiLCJpYXQiOjE2MDczNTI1MTcsIm5iZiI6MTYwNzM1MjUxNywiZXhwIjoxNjM4ODg4NTE3LCJzdWIiOiIzODUiLCJzY29wZXMiOltdfQ.Ix26ZW685ouEDylyxOAvZkMJvWEue7BhjJugBd1LBF4Gm2rxIsG3ec3qVlZDRLneK3RzdSrUF-p4qiTU9aWOZg"
    var cowpayAuthTokenTest: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImQ1OGUwYzQxMGNhYzFiYTYzNmZlZmY5ODMwNDhkNGFjNDg3MWQ3M2Q3OTI2ZGQ4ZDFmYWYwM2IzYWY1YTYyM2Y0OWY1NWRmOWQ1MGNhYTFjIn0.eyJhdWQiOiIxIiwianRpIjoiZDU4ZTBjNDEwY2FjMWJhNjM2ZmVmZjk4MzA0OGQ0YWM0ODcxZDczZDc5MjZkZDhkMWZhZjAzYjNhZjVhNjIzZjQ5ZjU1ZGY5ZDUwY2FhMWMiLCJpYXQiOjE2MDY5MzA2MzUsIm5iZiI6MTYwNjkzMDYzNSwiZXhwIjoxNjM4NDY2NjM1LCJzdWIiOiIzODciLCJzY29wZXMiOltdfQ.PTyQdVixvfIJRcsMWdId7TrwOuGGPaNIor24XsHLExNMPcOemh1nmw6sRWgem9kb4xKB73Q3DWcRnudv44vhDw"
    var cowpayAuthToken: String = if (BuildConfig.DEBUG) cowpayAuthTokenTest else cowpayAuthTokenProd
    var isFirebaseTokenUpdated: MutableLiveData<String> = MutableLiveData("")
    var adsList: MutableLiveData<List<AdUI>> = MutableLiveData()

    var isHealthCareProjectEnabled: Boolean = false
    var healthCareProjectStatusMsg: String = ""
    var isEditFollowersEnabled: Boolean = false
    var editFollowersStatusMsg: String = ""
    var hasQuestionnaire: MutableLiveData<Boolean> = MutableLiveData(false)
    var isSyndicatesListEnabled: MutableLiveData<Boolean> = MutableLiveData(false)
    var isCommitteesEnabled: MutableLiveData<Boolean> = MutableLiveData(false)
    var isClubEnabled: MutableLiveData<Boolean> = MutableLiveData(false)

    var YODAWY_CONFIG: MutableLiveData<AppConfigUI.YodawyStatus> = MutableLiveData()
    var VEZEETA_CONFIG: MutableLiveData<AppConfigUI.VezeetaStatus> = MutableLiveData()
    var CALL_CENTER = "0235317300"

    var DNS = if (BuildConfig.URL.contains("http")) BuildConfig.URL else "https://${BuildConfig.URL}"

    var OPAY_PAYMENT_CALLBACK_URL = (if (BuildConfig.DEBUG) "http://backend.neqabty.com:44392" else DNS) + "/api/v1/transactions/opay/callback"
    var OPAY_MERCHANT_ID = if (BuildConfig.DEBUG) "281821120532113" else "281821120144533"
    var OPAY_MERCHANT_NAME = if (BuildConfig.DEBUG) "neQabty TEST" else "neQabty"
    var OPAY_PUBLIC_KEY = if (BuildConfig.DEBUG) "OPAYPUB16386946354660.48619730311207143" else "OPAYPUB16383585551500.36105494805860716"
    var COWPAY_MODE = if (BuildConfig.DEBUG) CowpayConstantKeys.SandBox else CowpayConstantKeys.Production
    var OPAY_MODE = BuildConfig.DEBUG
    var CC_COMMISSION = .0288
    var POS_COMMISSION = .0101
    var FAWRY_COMMISSION = .0101
    var MIN_COMMISSION = 6.0
    var CLAIMING = 1
    var TRIPS = 2
    var RECORDS = 3
    var UPDATE_DATA = 4
    var COMPLAINTS = 5
    var QUESTIONNAIRE = 6
    var MEDICAL_RENEW = 7
    var ONLINE_PHARMACY = 8
    var TRACK_SHIPMENT = 9
    var CHANGE_USER_MOBILE = 10
    var CHANGE_PASSWORD = 11
    var MEDICAL_LETTERS = 12
    var MEDICAL_LETTERS_INQUIRY = 13
    var DOCTORS_RESERVATION = 14
    var COMMITTEES = 15

    var AD_MEDICAL_RENEW = 1
    var AD_TRIPS = 2
    var AD_RECORDS = 3
    var AD_COMPLAINTS = 4
    var AD_QUESTIONNAIRE = 5
    var AD_HOME = 6
    var AD_MEDICAL_DIRECTORY = 7
    var AD_ONLINE_PHARMACY = 8
    var AD_NEWS = 9
    var AD_PAYMENTS = 10

    var DELIVERY_LOCATION_SYNDICATE = 1
    var DELIVERY_LOCATION_HOME = 2
    var DELIVERY_LOCATION_MAIN_SYNDICATE = 3

    var PAYMENT_TYPE_TRIPS = "9991"
    var PAYMENT_TYPE_RECORDS = "9992"
    var PAYMENT_TYPE_MEDICAL_RENEW = "9993"

    sealed class Mode {
        class Visitor : Mode() {
            override fun toString(): String {
                return "visitor"
            }
        }

        class Verified : Mode() {
            override fun toString(): String {
                return "verified"
            }
        }
    }

    enum class PaymentOption {
        OpayCredit,
        OpayPOS,
        Fawry,
        MobileWallet
    }
}
