package vvu.study.kotlin.demo.ds.repos.impl
import kotlinx.coroutines.launch
import io.kotest.common.runBlocking
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.reactive.awaitFirst
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import reactor.test.StepVerifier
import vvu.study.kotlin.demo.config.DatastoreConfig
import vvu.study.kotlin.demo.ds.repos.inf.DeviceRepo
import vvu.study.kotlin.demo.dtos.Device
import vvu.study.kotlin.demo.exceptions.NotFoundException
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.*

@SpringBootTest( classes = [ DeviceRepoImpl::class, DatastoreConfig::class ] )
internal class DeviceRepoImplTest {

    @Autowired
    lateinit var deviceRepo: DeviceRepo

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
    fun `create a device`() {
        println(createADevice())
    }

    @Test
    fun `create a Device then find this one`() {
        val device = createADevice()

        runBlocking {

            // Create New Device
            val newId = deviceRepo.create(device).awaitFirst()

            // Find the new one
            val newDevice = deviceRepo.findById( device.id).awaitFirst()

            // Verify the Result
            newId shouldBe device.id
            newDevice shouldBe device
        }

    }

    @Test
    fun `find device by id but not found`() {

        val randomId = UUID.randomUUID().toString()

        val monoRes = deviceRepo.findById( randomId ).log()

        StepVerifier.create(monoRes).verifyError(NotFoundException::class.java)
    }

    @Test
    fun `get all devices`() {
        val device = createADevice()

        // Create New Device
        val monoRes = deviceRepo.create(device)

        StepVerifier.create(monoRes).expectNext(device.id).verifyComplete()

        val deviceFlux = deviceRepo.getDevices()
        val resDeviceFlux = deviceFlux.filter { it.id == device.id }
        StepVerifier.create(resDeviceFlux).expectNext(device).verifyComplete()

    }

    @Test
    fun `create, update and find device by id`() {
    }

}