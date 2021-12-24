package vvu.study.kotlin.demo.services.devices

import com.ninjasquad.springmockk.MockkBean
import io.kotest.common.runBlocking
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import vvu.study.kotlin.demo.ds.repos.inf.DeviceRepo
import vvu.study.kotlin.demo.dtos.Device
import vvu.study.kotlin.demo.dtos.utils.DeviceUtils
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest( classes = [ DeviceService::class ])
internal class DeviceServiceTest {

    @Autowired
    lateinit var service: DeviceService

    @MockkBean
    lateinit var deviceRepo : DeviceRepo

    @Test
    fun `Create a Device`() = runBlocking {

        val device = DeviceUtils.createADevice()
        val slotDevice = slot<Device>()
        val id = UUID.randomUUID().toString()

        every {
            deviceRepo.create( capture(slotDevice))
        } returns Mono.just( id )

        val newId = service.create( device )

        verify(exactly = 1) {
            deviceRepo.create(any())
        }

        slotDevice.captured shouldBeSameInstanceAs device
        newId shouldBe id
    }
}