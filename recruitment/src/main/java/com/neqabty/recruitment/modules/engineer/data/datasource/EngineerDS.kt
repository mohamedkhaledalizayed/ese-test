package com.neqabty.recruitment.modules.engineer.data.datasource

import com.neqabty.recruitment.modules.engineer.data.api.EngineerApi
import com.neqabty.recruitment.modules.engineer.data.model.engineerdata.EngineerModel
import com.neqabty.recruitment.modules.personalinfo.data.model.EngineerBody
import javax.inject.Inject

class EngineerDS @Inject constructor(private val engineerApi: EngineerApi) {

    suspend fun getEngineerData(): EngineerModel {
        return engineerApi.getEngineerData("3608662").engineer
    }

    suspend fun updateEngineerData(id: String, engineerBody: EngineerBody): EngineerModel {
        return engineerApi.updateEngineerData(id, engineerBody)
    }

}