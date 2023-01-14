package com.neqabty.chefaa.core.data

import com.neqabty.chefaa.modules.address.domain.entities.AddressEntity
import com.neqabty.chefaa.modules.orders.domain.entities.OrderItemsEntity

object Constants {

    //Seha
    const val BASE_URL_DEV = "http://3.131.229.146:7777/api/v1/chefaa/"
    const val BASE_URL_STAGING = "https://staging-community.neqabty.com/healthcare/api/v1/chefaa/"
    const val BASE_URL_PRO = "https://seha.neqabty.com/public/api/v1/chefaa/"


    var selectedAddress: AddressEntity? = null
    var userNumber: String = ""
    var mobileNumber: String = ""
    var countryCode: String = ""
    var nationalID: String = ""
    var name: String = ""
    var cart = Cart()

    enum class ITEMTYPES(val typeName: String) {
        IMAGE("image"),
        NOTE("text"),
        PRODUCT("product")
    }

    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"

    var jwt: String =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjJkOWE0YjJiYzFmMDU5YTJlM2VmNGNmMTAxMWM0ZmE1Njc4ZDk4YjVkNGNhNTllNDRkYTNkOTFlMzZhNTA3Y2RkYmE1ODA0MGNjOGI1OGVlIn0.eyJhdWQiOiIxIiwianRpIjoiMmQ5YTRiMmJjMWYwNTlhMmUzZWY0Y2YxMDExYzRmYTU2NzhkOThiNWQ0Y2E1OWU0NGRhM2Q5MWUzNmE1MDdjZGRiYTU4MDQwY2M4YjU4ZWUiLCJpYXQiOjE2MzcxMDcyOTUsIm5iZiI6MTYzNzEwNzI5NSwiZXhwIjoxNjY4NjQzMjk1LCJzdWIiOiIxNDA3NDUiLCJzY29wZXMiOltdfQ.UqgwdQJEZU8Md7_lFKfISDyP9sBXqls2WDgcO1oZkxkdJg-Xynk-7s0MttDhve_ouRJp1kvgzyzvHgvI2zMASB7S92FoFQYQPXBbLOkt5X3jMOuxUHTBge9VUzboHcceSkg8x5QYcZBVPXPPA858Scxj-QwqJQ376AQ1CYWWqNRY33B7oAUD_C1tlYG-TmRy28RDh-hLXXSDoyiFFxyy-aavebEqSmrR0G_U2LwzVyQr_OE5HJA_NhArzKOpeZDfPCJLj8794c0rEoyn3n_hF2ohCGerT7VyuDMjY65PhvMsHagQZByZ31vmPs7tbuWputEUv8hTf6Oh_HIJDZBSxExmQ5I5ju9T4u9nNEwJgWg8_M0a9K102pZqdMKoHZRoL1KK3jhmr9qt3JQPCfgUVzbQX2pRsdudSmhed5RIyKOpeI70lRlNPkuNhtN5QW0i2ZvDlTNu0-v8uTOfpmCOaODyfF0Gqs_RqVZ6_JDb3RHsujxD0v2aPzdz3gDWyRig2EtktQQ5YYh5ycaUR4jtCoK-2KN3vJ1me3l3p7R5lj2bgxTq9kfvczXvt_1fUig98h0gPzI2PmLCypq5ESavOWgJi6F8HfBk4Cke0_JHRc5yox04ejktrtRgIwBSb_SEtH3-zmwIh4NNXEMBGaMHVu1QspowNLQR65_ctKPHxI8"
    const val FIXED_TOKEN = "Basic 3f023e9dbcf1c467f19e8d8b4b546c02264bd207803927971e3b0b36e705b3d0"
}

data class Cart(
    val productList: MutableList<OrderItemsEntity> = mutableListOf(),
    val imageList: MutableList<OrderItemsEntity> = mutableListOf(),
    var note: OrderItemsEntity? = null
) {
    val size: Int
        get() {
            val x = productList.size + imageList.size
            if (note != null)
                return x + 1
            return x
        }

    fun getChildrenCounter(): Int {
        var count = imageList.size
        this.productList.forEach {
            count += it.quantity
        }
        if (note != null)
            return count + 1
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