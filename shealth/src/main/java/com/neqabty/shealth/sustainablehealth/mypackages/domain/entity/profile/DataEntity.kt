package com.neqabty.shealth.sustainablehealth.mypackages.domain.entity.profile



data class DataEntity(
    val client: ClientEntity,
    val subscribedPackages: List<SubscribedPackageEntity>,
    val wallet: WalletEntity
)