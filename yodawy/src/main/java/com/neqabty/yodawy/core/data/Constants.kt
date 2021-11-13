package com.neqabty.yodawy.core.data

import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity

object Constants {
    var yodawyId: String = ""
    var selectedAddressId: String = ""
    var userNumber: String = ""
    var mobileNumber: String = ""
    var jwt: String = ""
    var cartItems= mutableListOf<ProductEntity>()
}