package com.neqabty.domain.entities

data class SyndicateServicesEntity(
    var typesList: List<ServiceType>?,
    var servicesList: List<Service>?
) {
    data class ServiceType(
        var id: Int = 0,
        var type: String?
    )

    data class Service(
        var id: Int = 0,
        var groupID: Int = 0,
        var name: String?,
        var price: String?
    )
}