package com.example.model

import io.micronaut.serde.annotation.Serdeable.Serializable
import io.micronaut.serde.annotation.Serdeable.Deserializable
import java.util.UUID

@Serializable
@Deserializable
data class Cart(
    val id: String,
    val name: String,
    val destination: String?,
    val receiver: String?,
    val classification: String?
)
