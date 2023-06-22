package com.neqabty.healthcare.sustainablehealth.subscribtions.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.SubscribePostBodyRequest
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.UpdatePackageBody
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.relationstypes.Relation
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.subscription.SubscriptionModel
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.terms.DataModel
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.terms.TermsModel
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.updatepackage.UpdatePackageModel
import com.neqabty.healthcare.sustainablehealth.subscribtions.data.source.SubscriptionSource
import com.neqabty.healthcare.sustainablehealth.subscribtions.domain.entity.relations.RelationEntity
import com.neqabty.healthcare.sustainablehealth.subscribtions.domain.entity.subscribtions.SubscriptionEntity
import com.neqabty.healthcare.sustainablehealth.subscribtions.domain.entity.terms.TermsEntity
import com.neqabty.healthcare.sustainablehealth.subscribtions.domain.entity.terms.TermsEntityList
import com.neqabty.healthcare.sustainablehealth.subscribtions.domain.entity.updatepackage.UpdatePackageEntity
import com.neqabty.healthcare.sustainablehealth.subscribtions.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class SubscriptionRepositoryImpl @Inject constructor(private val subscriptionSource: SubscriptionSource) :
    SubscriptionRepository {
    override fun getRelations(): Flow<List<RelationEntity>> {
        return flow {
            emit(subscriptionSource.getRelations().map { it.toRelationEntity() })
        }
    }

    override fun addSubscription(
        subscribePostBodyRequest: SubscribePostBodyRequest
    ): Flow<SubscriptionEntity> {
        return flow {
            emit(
                subscriptionSource.addSubscription(subscribePostBodyRequest).toSubscriptionEntity()
            )
        }
    }

    override fun updatePackage(
        updatePackageBody: UpdatePackageBody
    ): Flow<UpdatePackageEntity> {
        return flow {
            emit(
                subscriptionSource.updatePackage(updatePackageBody).toUpdatePackageEntity()
            )
        }
    }

    override fun getTermsAndConditions(id: String): Flow<TermsEntityList> {
        return flow {
            emit(subscriptionSource.getTermsAndConditions(id).toTermsEntityList())
        }
    }
}

private fun TermsModel.toTermsEntityList(): TermsEntityList{
    return TermsEntityList(
        data = data.map { it.toTermsEntity() },
        message = message,
        status = status,
        status_code = status_code
    )
}

private fun DataModel.toTermsEntity(): TermsEntity{
    return TermsEntity(
        created_at = created_at,
        id = id,
        notes = notes,
        package_id = package_id,
        terms_document = terms_document,
        updated_at = updated_at
    )
}

private fun SubscriptionModel.toSubscriptionEntity(): SubscriptionEntity{
    return SubscriptionEntity(
        status = status,
        message = message
    )
}

private fun UpdatePackageModel.toUpdatePackageEntity(): UpdatePackageEntity{
    return UpdatePackageEntity(
        status = status,
        message = message
    )
}

private fun Relation.toRelationEntity(): RelationEntity{
    return RelationEntity(
        id = id,
        relation = relation
    )
}

private fun String.toBase64(): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    val bitmap = BitmapFactory.decodeFile(this)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStream)
    val imageBytes = byteArrayOutputStream.toByteArray()
    val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
    return imageString
}
