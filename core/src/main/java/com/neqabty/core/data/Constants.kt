package com.neqabty.core.data


object Constants {

    //Seha
    const val BASE_URL_DEV = "https://seha.neqabty.com/public/api/v1/"
    const val BASE_URL_PRO = "https://seha.neqabty.com/public/api/v1/"

    //Syndicates
    const val BASE_URL_DEV_SYNDICATE = "https://neqabty.et3.co/api/"
    private const val BASE_URL_STAGING_SYNDICATE = "https://staging-community.neqabty.com/api/"

    //News
    const val BASE_URL_DEV_NEWS = "https://news.et3.co/api/"
    const val BASE_URL_STAGING_NEWS = "https://staging-news.neqabty.com/api/"

    //OTP
    const val BASE_URL_DEV_OTP = "https://neqabty.et3.co/"
    const val BASE_URL_STAGING_OTP = "https://staging-community.neqabty.com/"

    //Main
    const val BASE_URL_Main = BASE_URL_STAGING_SYNDICATE

    const val NEQABTY_CODE = "e00"
    const val ESE_CODE = "e03"
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
}