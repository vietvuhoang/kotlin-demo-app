package vvu.study.kotlin.demo.dtos

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

internal class DeviceTest {

    private companion object {
        val deviceTypes = listOf("washing-machine", "refrigerator", "vacuum")

        fun createADevice()  = Device(
            id = UUID.randomUUID().toString(),
            productionCode = "PC-${UUID.randomUUID().toString().split("-")[0]}",
            serialNumber = UUID.randomUUID().toString(),
            deviceType = deviceTypes.asSequence().shuffled().first(),
            registrationDate = LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS)
        )
    }

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun testToString() {

        val device = createADevice()
        println(device)
        val kMapper = ObjectMapper().registerModule(JavaTimeModule()).registerModule(KotlinModule())
        val s = kMapper.writeValueAsString(device)
        println(s)

        val dateTimeS = "2021-12-22T11:15:08.970Z"

        val jsonValue = "{\"id\":\"e2051e57-a056-409f-aac7-b749eedfb071\",\"productionCode\":\"PC-bc4425bd\",\"serialNumber\":\"f1397513-39e9-42da-859c-791733097d54\",\"deviceType\":\"washing-machine\",\"registrationDate\":\"2021-12-22T14:02:58.059Z\"}\n"

        kMapper.readValue<Device>(jsonValue)

        println(LocalDateTime.parse(dateTimeS, DateTimeFormatter.ISO_DATE_TIME))

    }
}