package com.neqabty.healthcare.core.data




object Constants {


    //TODO move this to gradle
    //Very Important Before Publishing
    //Seha
    const val BASE_URL_DEV = "http://3.131.229.146:7777/api/v1/"
    const val BASE_URL_STAGING = "https://staging-community.neqabty.com/healthcare/api/v1/"
    const val BASE_URL_PRO = "https://seha.neqabty.com/public/api/v1/"

    //Syndicates
    const val BASE_URL_DEV_SYNDICATE = "https://dev-community.neqabty.com/api/"
    private const val BASE_URL_STAGING_SYNDICATE = "https://staging-community.neqabty.com/api/"
    private const val BASE_URL_PRO_SYNDICATE = "https://community.neqabty.com/api/"

    //News
    const val BASE_URL_DEV_NEWS = "https://news.et3.co/api/"
    const val BASE_URL_STAGING_NEWS = "https://staging-news.neqabty.com/api/"
    const val BASE_URL_PRO_NEWS = "https://news.neqabty.com/api/"

    //OTP
    const val BASE_URL_DEV_OTP = "https://dev-community.neqabty.com/"
    const val BASE_URL_STAGING_OTP = "https://staging-community.neqabty.com/"
    const val BASE_URL_PRO_OTP = "https://community.neqabty.com/"

    //Main
    const val BASE_URL_Main = BASE_URL_STAGING_SYNDICATE
    // for payment true for testing false for production
    const val SANDBOX = false

    // End

    const val NEQABTY_CODE = "e00"
    const val ESE_CODE = "e03"
    const val TOUR_GUIDES_CODE = "e01"
    const val NATURAL_THERAPY_CODE = "e02"
    const val AGRICULTURE_CODE = "e05"
    const val TOGAREEN_CODE = "e06"
    var isSyndicateMember = false
    var selectedSyndicateCode = ""
    var selectedSyndicatePosition = 0
    const val ENCRYPT_KEY = "neqabty@seha$"
    const val PREFS_FILE  = "neqabty_prefs"
    //Tour Guide
    const val TOUR_GUIDE_SYNDICATE_EMAIL = "gts@neqabty.com"
    const val TOUR_GUIDE_SYNDICATE_LAT = 0.0
    const val TOUR_GUIDE_SYNDICATE_LONG = 0.0
    const val TOUR_GUIDE_SYNDICATE_PHONE = "25899882 -25890438 - 01144300335"
    const val TOUR_GUIDE_SYNDICATE_ADDRESS = ""

    //Neqabty
    const val NEQABTY_EMAIL = "medical@neqabty.com"
    const val NEQABTY_LAT = 30.0413513
    const val NEQABTY_LONG = 31.2005282
    const val NEQABTY_PHONE = "٠٢٣٥٣٥٦٨٦٨"
    const val NEQABTY_ADDRESS = "٥٤ محي الدين ابو العز الدقي الجيزة"

    var GENERAL_NEWS = 1
    var SYNDICATE_NEWS = 2
}
