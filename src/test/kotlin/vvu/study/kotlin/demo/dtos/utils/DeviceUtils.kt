package vvu.study.kotlin.demo.dtos.utils

import vvu.study.kotlin.demo.dtos.Device
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*

class DeviceUtils {
    companion object {
        val deviceTypes = listOf("washing-machine", "refrigerator", "vacuum")

        fun createADevice()  = Device(
            id = UUID.randomUUID().toString(),
            productionCode = "PC-${UUID.randomUUID().toString().split("-")[0]}",
            serialNumber = UUID.randomUUID().toString(),
            deviceType = deviceTypes.asSequence().shuffled().first(),
            registrationDate = LocalDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS)
        )
    }
}