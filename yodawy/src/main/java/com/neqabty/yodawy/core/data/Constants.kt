package com.neqabty.yodawy.core.data

import android.net.Uri
import com.neqabty.yodawy.modules.address.domain.entity.AddressEntity
import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity

object Constants {
    var yodawyId: String = ""
    lateinit var selectedAddress: AddressEntity
    var userNumber: String = ""
    var mobileNumber: String = ""
    var jwt: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjNmZDA1ZDc0NTdlYjIwM2YzNzJhMDE3ZDUzZTE4MWU1ZWVmODI5MTAxNmQ4OTBhMjc4YmNjMDk0OWZmN2FkOWY1MGIzZDM2NjQ0OTFmM2RlIn0.eyJhdWQiOiIxIiwianRpIjoiM2ZkMDVkNzQ1N2ViMjAzZjM3MmEwMTdkNTNlMTgxZTVlZWY4MjkxMDE2ZDg5MGEyNzhiY2MwOTQ5ZmY3YWQ5ZjUwYjNkMzY2NDQ5MWYzZGUiLCJpYXQiOjE2MzcwNTkzNDYsIm5iZiI6MTYzNzA1OTM0NiwiZXhwIjoxNjY4NTk1MzQ2LCJzdWIiOiIxMTcxMjgiLCJzY29wZXMiOltdfQ.OPMwR7zOmMaP6FSYII2Vg-4sw0zNPH1qRZs4MPfwe7_JTR08_lQtfGbcaz5JhHrL6-OZsb_TUIe9bwECDUA5TtSyGQergPiWXXjbG4w8tcHw6KxMGTVkFJNRWM1Py2gjqRdhwdFnJQq599j3TAk7OtnBfMuNPAFVJNJtwYXhPpOkhk8990JeXVpgxZZfnSaayQpWuN5nL--g6wZruBFMwbyWWgJEuu0-VmtnG3gCbZXEzm_duBHz4gGhQqL8CiYGguyvt5Bt0kmsL8c7UUt5HoGT8NHWteRDTrUbkeLzFUlZ8ZZ1pk-gs4PFFywd7MY_OqwB8vDw7IRx9yv6N2L45nrBRsSMNE1L-mJh6PM_ZxMTiZZ3yaMMaJXCrQjX1h3yN1ktocZt7fgcLbo7-HRcgFSqYNdolekc2aDxOwf7a--UYatpC3hdscrNXfxw6WtCVIZT7jKOdqX_ksPiw6QAaYncgs3JFDWB8n15DU5sBab-2oZt5-jsjA_2IWrDhPjMiOPm6Wd9WSl1QIURyjVAVhVGrQAP8kRUOBnWMlYbDcBBVk8va--Te9rEzCPXjS4bRFAKVBbSp7tdDhqMy_o7WfNq6Cv282Is9VO79m9WMAI83ISTupoz2zz3sMSk_fpN6024jEQ0TSyJVuxD6PyJP_i7j9O0sF0Cbc7deIXFibI"
    var cartItems= mutableListOf<Pair<ProductEntity,Int>>()
    var imageList= mutableListOf<Uri>()


    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"
}