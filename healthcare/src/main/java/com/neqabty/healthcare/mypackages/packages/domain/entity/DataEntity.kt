package com.neqabty.healthcare.mypackages.packages.domain.entity



data class DataEntity(
    val client: ClientEntity,
    val subscribedPackages: List<SubscribedPackageEntity>,
    val wallet: WalletEntity
)