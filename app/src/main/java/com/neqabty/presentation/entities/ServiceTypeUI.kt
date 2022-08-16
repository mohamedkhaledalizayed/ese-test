package com.neqabty.presentation.entities

data class ServiceTypeUI(
    var typesList: List<ServiceType>?,
    var servicesList: List<Service>?
) {
    data class ServiceType(
        var id: Int = 0,
        var type: String?
    ){
        override fun toString(): String {
            return type ?: ""
        }
    }

    data class Service(
        var id: Int = 0,
        var groupID: Int = 0,
        var name: String?,
        var price: String?
    ){
        override fun toString(): String {
            return name ?: ""
        }
    }
}