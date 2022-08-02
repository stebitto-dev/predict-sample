package com.stebitto.agify.impl.source.remote

import kotlinx.serialization.Serializable

@Serializable
internal data class AgifyResponseModel(
    val name: String,
    val age: Int
)