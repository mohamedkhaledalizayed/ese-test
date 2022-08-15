package com.neqabty.presentation.entities

data class RefundUI(
    var id: Int,
    var name: String,
    var mobile: String,
    var userNumber: String,
    var benId: String,
    var description: String,
    var branchProfileId: String,
    var serviceProviderId: String,
    var letterTypeId: String,
    var mobileToken: String,
    var status: String,
    var syndicate_request_id: String,
    var created_at: String
)