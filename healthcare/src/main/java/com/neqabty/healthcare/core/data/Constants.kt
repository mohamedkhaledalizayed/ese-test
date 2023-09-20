package com.neqabty.healthcare.core.data


import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.neqabty.healthcare.chefaa.address.domain.entities.AddressEntity
import com.neqabty.healthcare.chefaa.orders.domain.entities.OrderItemsEntity
import com.neqabty.healthcare.packages.subscription.data.model.Followers
import com.neqabty.healthcare.pharmacymart.address.domain.entity.PharmacyMartAddressEntity


object Constants {

    const val forTesting = false

    //TODO move this to gradle
    //Very Important Before Publishing
    //Seha
    const val BASE_URL_DEV = "https://dev-community.neqabty.com/healthcare/api/v1/"
    const val BASE_URL_STAGING = "https://staging-community.neqabty.com/healthcare/api/v1/"
    const val BASE_URL_PRO = "https://community.neqabty.com/healthcare/api/v1/"

    //Chefaa
    const val BASE_URL_DEV_CHEFAA = "https://dev-community.neqabty.com/healthcare/api/v1/chefaa/"
    const val BASE_URL_STAGING_CHEFAA = "https://staging-community.neqabty.com/healthcare/api/v1/chefaa/"
    const val BASE_URL_PRO_CHEFAA = "https://community.neqabty.com/healthcare/api/v1/chefaa/"

    //Syndicates
    const val BASE_URL_DEV_SYNDICATE = "https://dev-community.neqabty.com/api/"
    private const val BASE_URL_STAGING_SYNDICATE = "https://staging-community.neqabty.com/api/"
    private const val BASE_URL_PRO_SYNDICATE = "https://community.neqabty.com/api/"

    //Retirement
    const val BASE_URL_RETIREMENT = "https://edupen.neqabty.com/api/"
    //News
    const val BASE_URL_DEV_NEWS = "https://dev-community.neqabty.com/api/"
    const val BASE_URL_STAGING_NEWS = "https://staging-community.neqabty.com/api/"
    const val BASE_URL_PRO_NEWS = "https://community.neqabty.com/api/"

    //OTP
    const val BASE_URL_DEV_OTP = "https://dev-community.neqabty.com/"
    const val BASE_URL_STAGING_OTP = "https://staging-community.neqabty.com/"
    const val BASE_URL_PRO_OTP = "https://community.neqabty.com/"

    //Main
    const val BASE_URL_Main = BASE_URL_DEV_SYNDICATE
    // for payment true for testing false for production
    const val SANDBOX = false
    // End

    var isFirebaseTokenUpdated: MutableLiveData<String> = MutableLiveData("")

    const val NEQABTY_CODE = "e00"
    const val ESE_CODE = "e03"
    const val TOUR_GUIDES_CODE = "e01"
    const val MORSHEDIN_CODE = "e01"
    const val NATURAL_THERAPY_CODE = "e02"
    const val AGRICULTURE_CODE = "e05"
    const val TOGAREEN_CODE = "e06"
    const val AGRI_CODE = "e05"
    const val VISA = "visa"
    const val BANK = "bank"
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

    var DOCTORS_RESERVATION_URL = ""
    var MEDICINE_URL = ""
    var CHEFAA_SUPPORT_NUMBER = "0221294341"
    var selectedAddress: AddressEntity? = null
    var selectedAddressPharmacyMart: PharmacyMartAddressEntity? = null
    var userNumber: String = ""
    var mobileNumber: String = ""
    var countryCode: String = ""
    var nationalID: String = ""
    var name: String = ""
    var cart = Cart()
    var pharmacyMartCart = PharmacyMartCart()
    var latitude = 30.043963618425664
    var longitude = 31.234388016164303
    enum class ITEMTYPES(val typeName: String) {
        IMAGE("image"),
        NOTE("text"),
        PRODUCT("product")
    }

    val listOfFollowers = ArrayList<Followers>()

    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"

    var jwt: String =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjJkOWE0YjJiYzFmMDU5YTJlM2VmNGNmMTAxMWM0ZmE1Njc4ZDk4YjVkNGNhNTllNDRkYTNkOTFlMzZhNTA3Y2RkYmE1ODA0MGNjOGI1OGVlIn0.eyJhdWQiOiIxIiwianRpIjoiMmQ5YTRiMmJjMWYwNTlhMmUzZWY0Y2YxMDExYzRmYTU2NzhkOThiNWQ0Y2E1OWU0NGRhM2Q5MWUzNmE1MDdjZGRiYTU4MDQwY2M4YjU4ZWUiLCJpYXQiOjE2MzcxMDcyOTUsIm5iZiI6MTYzNzEwNzI5NSwiZXhwIjoxNjY4NjQzMjk1LCJzdWIiOiIxNDA3NDUiLCJzY29wZXMiOltdfQ.UqgwdQJEZU8Md7_lFKfISDyP9sBXqls2WDgcO1oZkxkdJg-Xynk-7s0MttDhve_ouRJp1kvgzyzvHgvI2zMASB7S92FoFQYQPXBbLOkt5X3jMOuxUHTBge9VUzboHcceSkg8x5QYcZBVPXPPA858Scxj-QwqJQ376AQ1CYWWqNRY33B7oAUD_C1tlYG-TmRy28RDh-hLXXSDoyiFFxyy-aavebEqSmrR0G_U2LwzVyQr_OE5HJA_NhArzKOpeZDfPCJLj8794c0rEoyn3n_hF2ohCGerT7VyuDMjY65PhvMsHagQZByZ31vmPs7tbuWputEUv8hTf6Oh_HIJDZBSxExmQ5I5ju9T4u9nNEwJgWg8_M0a9K102pZqdMKoHZRoL1KK3jhmr9qt3JQPCfgUVzbQX2pRsdudSmhed5RIyKOpeI70lRlNPkuNhtN5QW0i2ZvDlTNu0-v8uTOfpmCOaODyfF0Gqs_RqVZ6_JDb3RHsujxD0v2aPzdz3gDWyRig2EtktQQ5YYh5ycaUR4jtCoK-2KN3vJ1me3l3p7R5lj2bgxTq9kfvczXvt_1fUig98h0gPzI2PmLCypq5ESavOWgJi6F8HfBk4Cke0_JHRc5yox04ejktrtRgIwBSb_SEtH3-zmwIh4NNXEMBGaMHVu1QspowNLQR65_ctKPHxI8"


}

data class PharmacyMartCart(
    val pharmacyMartImageList: MutableList<Uri?> = mutableListOf(),
    var orderByText: String = ""
)

data class Cart(
    val productList: MutableList<OrderItemsEntity> = mutableListOf(),
    val imageList: MutableList<OrderItemsEntity> = mutableListOf(),
    var note: OrderItemsEntity? = null
) {
    val size: Int
        get() {
            return productList.size
        }

    fun getChildrenCounter(): Int {
        var count = 0
        this.productList.forEach {
            count += it.quantity
        }
        return count
    }

    fun getOrderItems(): List<OrderItemsEntity> {
        val items = mutableListOf<OrderItemsEntity>()
        note?.let { items.add(it) }
        items.addAll(productList)
        items.addAll(imageList)
        return items
    }
}