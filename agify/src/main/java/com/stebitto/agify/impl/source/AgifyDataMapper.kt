package com.stebitto.agify.impl.source

import com.stebitto.agify.impl.source.remote.AgifyResponseModel
import com.stebitto.commondto.AgifyDTO
import javax.inject.Inject

internal class AgifyDataMapper @Inject constructor() {
    fun map(apiModel: AgifyResponseModel) = AgifyDTO(apiModel.name, apiModel.age)
}