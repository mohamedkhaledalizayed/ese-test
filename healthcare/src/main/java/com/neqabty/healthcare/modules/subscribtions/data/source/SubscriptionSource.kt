package com.neqabty.healthcare.modules.subscribtions.data.source

import com.neqabty.healthcare.modules.subscribtions.data.api.SubscriptionApi
import com.neqabty.healthcare.modules.subscribtions.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.modules.subscribtions.data.model.relationstypes.Relation
import com.neqabty.healthcare.modules.subscribtions.data.model.relationstypes.RelationsTypesModel
import javax.inject.Inject

class SubscriptionSource @Inject constructor(private val subscriptionApi: SubscriptionApi) {

    suspend fun getRelations(): List<Relation>{
        return subscriptionApi.getRelations().data.relations
    }
    suspend fun addSubscription(subscribePostBodyRequest: SubscribePostBodyRequest): Boolean {
        val p = subscribePostBodyRequest.personalImage.length

        return subscriptionApi.addSubscription(
            token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI5Njg0MjgyOS0wNDVlLTQxM2ItODQ2My0xMTU4ZWZlNTk2N2UiLCJqdGkiOiJmMzZhNTEyNDBmOGYwMzQ4YzJiNmE3NmJjODNjYzNhZDViMzkzMjdjN2UwZmM0YmI2YmQ3YzViMjU0YjBmZDMwYWYzYmY3ZDM0NmI1OGQ3MSIsImlhdCI6MTY1NTEzMDEwMC43NTA5NDIsIm5iZiI6MTY1NTEzMDEwMC43NTA5NTksImV4cCI6MTY3MDk0MTMwMC41ODkxMTUsInN1YiI6IjEiLCJzY29wZXMiOltdfQ.IupOzZzZfjzRKRNAV6U3KU5foa2NaUVDVO3gB9hZyLpGw0O207bAYqcjuopbe6Urv5a-XadrekeirmrZKvHMPTmkg86mF4idEY1b0_MJGOkHHwHMN9AWbvUoCu6uFRSAmvCBRUzXc3OR2RELZCI4NNOK8OowBUSrt43ZTxrgM7-auSYZ3wjhzR3KoB0PGUJ9kJfhNxLcUCxQfXw7w1dsx13jGxjAfgcIfNzskEpwyVDck4D1XL4uPYc42F1iLkvv2uvh9kP1ncuc5QVY8PXWdEWAG3-MVcEgH2cOwaZtZs4fxkI_g8ga3PfDjkB8MdFeV9cH7LH-4zyDhAmf3KUekh4k6cXVwF3Sh7TmZL4ppfBUInEnA-CpfygcQj7z9ltg2aCs0EYXe4BUfNzQTRE7JJMcT9umNV3wWaeBJyZZG6FSE1t4Tfj46IYT5qmKeUsKJ6T4QPnA2ODGeBzNBImOCU4RCGFNMkLNTGOF2o69CaWmx5GozLc65tFcXIWuibjhUnON8J3Z4QwRWjMNxkA4BMRjhOgE6Zx2dCcnr6CQ28MAp0ARRc9utOcWSSzp2uHPHNt-XIM3AuJ4klxXpzBcmuWBPMAEzLZg1iVz-q1SfHHWrhZiCI2LiqXdencsrMoZ0RUMXVTRQCo3ARKO5TwK7122T9Hox3SsHuyXe6udUio",
            subscribePostBodyRequest = subscribePostBodyRequest
        ).status
    }
}