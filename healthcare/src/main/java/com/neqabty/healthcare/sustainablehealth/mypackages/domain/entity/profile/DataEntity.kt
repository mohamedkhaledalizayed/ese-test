package com.neqabty.healthcare.sustainablehealth.mypackages.domain.entity.profile



data class DataEntity(
    val client: ClientEntity,
    val subscribedPackages: List<SubscribedPackageEntity>,
    val wallet: WalletEntity
)