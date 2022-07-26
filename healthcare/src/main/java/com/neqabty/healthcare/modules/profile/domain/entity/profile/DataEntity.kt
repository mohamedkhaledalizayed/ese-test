package com.neqabty.healthcare.modules.profile.domain.entity.profile



data class DataEntity(
    val client: ClientEntity,
    val subscribedPackages: List<SubscribedPackageEntity>,
    val wallet: WalletEntity
)