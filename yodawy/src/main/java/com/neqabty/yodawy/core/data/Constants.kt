package com.neqabty.yodawy.core.data

import android.net.Uri
import com.neqabty.yodawy.modules.address.domain.entity.AddressEntity
import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity

object Constants {
    var yodawyId: String = ""
    lateinit var selectedAddress: AddressEntity
    var userNumber: String = ""
    var mobileNumber: String = ""
    var jwt: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjJkOWE0YjJiYzFmMDU5YTJlM2VmNGNmMTAxMWM0ZmE1Njc4ZDk4YjVkNGNhNTllNDRkYTNkOTFlMzZhNTA3Y2RkYmE1ODA0MGNjOGI1OGVlIn0.eyJhdWQiOiIxIiwianRpIjoiMmQ5YTRiMmJjMWYwNTlhMmUzZWY0Y2YxMDExYzRmYTU2NzhkOThiNWQ0Y2E1OWU0NGRhM2Q5MWUzNmE1MDdjZGRiYTU4MDQwY2M4YjU4ZWUiLCJpYXQiOjE2MzcxMDcyOTUsIm5iZiI6MTYzNzEwNzI5NSwiZXhwIjoxNjY4NjQzMjk1LCJzdWIiOiIxNDA3NDUiLCJzY29wZXMiOltdfQ.UqgwdQJEZU8Md7_lFKfISDyP9sBXqls2WDgcO1oZkxkdJg-Xynk-7s0MttDhve_ouRJp1kvgzyzvHgvI2zMASB7S92FoFQYQPXBbLOkt5X3jMOuxUHTBge9VUzboHcceSkg8x5QYcZBVPXPPA858Scxj-QwqJQ376AQ1CYWWqNRY33B7oAUD_C1tlYG-TmRy28RDh-hLXXSDoyiFFxyy-aavebEqSmrR0G_U2LwzVyQr_OE5HJA_NhArzKOpeZDfPCJLj8794c0rEoyn3n_hF2ohCGerT7VyuDMjY65PhvMsHagQZByZ31vmPs7tbuWputEUv8hTf6Oh_HIJDZBSxExmQ5I5ju9T4u9nNEwJgWg8_M0a9K102pZqdMKoHZRoL1KK3jhmr9qt3JQPCfgUVzbQX2pRsdudSmhed5RIyKOpeI70lRlNPkuNhtN5QW0i2ZvDlTNu0-v8uTOfpmCOaODyfF0Gqs_RqVZ6_JDb3RHsujxD0v2aPzdz3gDWyRig2EtktQQ5YYh5ycaUR4jtCoK-2KN3vJ1me3l3p7R5lj2bgxTq9kfvczXvt_1fUig98h0gPzI2PmLCypq5ESavOWgJi6F8HfBk4Cke0_JHRc5yox04ejktrtRgIwBSb_SEtH3-zmwIh4NNXEMBGaMHVu1QspowNLQR65_ctKPHxI8"
    var cartItems= mutableListOf<Pair<ProductEntity,Int>>()
    var imageList= mutableListOf<Uri>()


    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"


    var FIXED_TOKEN = "Basic 3f023e9dbcf1c467f19e8d8b4b546c02264bd207803927971e3b0b36e705b3d0"
    var YODAWY_URL = ""
}