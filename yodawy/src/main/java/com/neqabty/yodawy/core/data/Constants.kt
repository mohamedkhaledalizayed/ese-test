package com.neqabty.yodawy.core.data

import android.net.Uri
import com.neqabty.yodawy.modules.address.domain.entity.AddressEntity
import com.neqabty.yodawy.modules.products.domain.entity.ProductEntity

object Constants {
    var yodawyId: String = ""
    lateinit var selectedAddress: AddressEntity
    var userNumber: String = ""
    var mobileNumber: String = ""
    var jwt: String = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijc1NTdjYWQ4YjYwNzU5Nzg5MmQxZGZlZmYwMGRiNTk2MGJlYzBjYjQwYjM5ZDY3MDhlZTZkYmJjYmEwYzNjZGE4OTE2Zjk4NzdkNGVhMmVjIn0.eyJhdWQiOiIxIiwianRpIjoiNzU1N2NhZDhiNjA3NTk3ODkyZDFkZmVmZjAwZGI1OTYwYmVjMGNiNDBiMzlkNjcwOGVlNmRiYmNiYTBjM2NkYTg5MTZmOTg3N2Q0ZWEyZWMiLCJpYXQiOjE2MzY5MTE0OTksIm5iZiI6MTYzNjkxMTQ5OSwiZXhwIjoxNjY4NDQ3NDk5LCJzdWIiOiIxMTcxMjgiLCJzY29wZXMiOltdfQ.DteecElZ2fhlj6ToOMcuMYVBxaQZ_g-19Onij987YYuZZaYB6_qQ_Oacr36XkeCG0A7edHDVq1o0G7Z-NQOAyM6JyxtIs0ocKoMq1Hnk2ZRj-PfD1z2btivqqVGGPXAyxw6q7YraUmXrjEcG7FwTvKIFEG6F2i2haulWeVqFpE3wJO_lVgACqORZ_mEmX3X5Goy8R0VpKl_6Yls4hkgGp8SNgPdHn7vaaevOOgnZUqNGU6YurC9uBsOqW3AnaJTWeD3jWR0gmqNKIs6ap6erHGhkAoobUU7uLtiZ0gOcULfBAZMLlsXygiaJvCkLOaEr7cH_wG0NYGwbILuN0iMPJ5n1eCzRk0AEt-UPHrqLuwHHzdKAKYAKgOj-JDW299jWBB_orRaCWs7Zg4DgddAdeVyjck26A0lMGdYHI7vqIieE8n98m8FcKLt6gfxK-btflhyk_ARNweMv9sVGOGBXAHnLL5eGeVSaLXXpAOqh3xz7Wnt5j7gS715511813CaObB5DOD933oLl9mdExVs6XIV-Eua8yhtVmV7TozPpJ-gNSiBLBLodCf2KoAcMVzJidL_ck9C3SzencJEpqhRfCU-Zl_qkHw0P6VglhWH53KJTL5wkJaYtqIISOzZ_60zybqrVSb_064o-JSrf5nHhIUPmVhNlzZUAQEd1q8VvmUw"
    var cartItems= mutableListOf<Pair<ProductEntity,Int>>()
    var imageList= mutableListOf<Uri>()
}