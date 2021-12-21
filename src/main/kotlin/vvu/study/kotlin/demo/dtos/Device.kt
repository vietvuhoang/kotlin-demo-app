package vvu.study.kotlin.demo.dtos

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import vvu.study.kotlin.demo.jackson.LocalDateTimeJsonDeserializer
import vvu.study.kotlin.demo.jackson.LocalDateTimeJsonSerializer
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*

data class Device (
    val id: String = UUID.randomUUID().toString(),
    val productionCode : String,
    val serialNumber: String,
    val deviceType: String,
    @JsonSerialize(using = LocalDateTimeJsonSerializer::class)
    @JsonDeserialize(using = LocalDateTimeJsonDeserializer::class)
    val registrationDate : LocalDateTime = LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS)
) : Dto

data class DeviceEvent(
    val id: String,
    val deviceId: String,
    val eventType: String,
    val payload: String,
    val eventTimestamp: LocalDateTime
) : Dto
