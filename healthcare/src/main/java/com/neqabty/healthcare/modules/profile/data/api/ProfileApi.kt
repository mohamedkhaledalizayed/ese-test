package com.neqabty.healthcare.modules.profile.data.api

import com.neqabty.healthcare.modules.profile.data.model.AddFollowerBody
import com.neqabty.healthcare.modules.profile.data.model.DeleteFollowerBody
import com.neqabty.healthcare.modules.profile.data.model.deletefollower.DeleteFollowerModel
import com.neqabty.healthcare.modules.profile.data.model.profile.ProfileModel
import com.neqabty.healthcare.modules.profile.data.model.relationstypes.RelationsTypesModel
import retrofit2.http.*

interface ProfileApi {

    @FormUrlEncoded
    @POST("client/profile")
    suspend fun getProfile(@Field("mobile") phone: String): ProfileModel

    @GET("general-Lockups")
    suspend fun getRelations(): RelationsTypesModel

    @POST("vendor/subscribtions/follower-request")
    suspend fun addFollower(@Body addFollowerBody: AddFollowerBody): String

    @POST("vendor/subscribtions/follower-delete")
    suspend fun deleteFollower(@Body deleteFollowerBody: DeleteFollowerBody): DeleteFollowerModel

}