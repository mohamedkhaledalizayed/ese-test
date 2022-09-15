package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response


data class SyndicateServicesData(
    @field:SerializedName("ServiceGroupList")
    var typesList: List<ServiceType>,
    @field:SerializedName("ServiceList")
    var servicesList: List<Service>
) : Response(){
    data class ServiceType(
        @field:SerializedName("ServiceGroupID")
        var id: Int = 0,
        @field:SerializedName("ServiceGroupName")
        var type: String?
    )

    data class Service(
        @field:SerializedName("ServiceId")
        var id: Int = 0,
        @field:SerializedName("ServiceGroupId")
        var groupID:  Int = 0,
        @field:SerializedName("ServiceName")
        var name: String?,
        @field:SerializedName("TotalPrice")
        var price: String?
    )


}