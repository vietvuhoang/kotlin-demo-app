package vvu.study.kotlin.demo.controllers

import com.ninjasquad.springmockk.MockkBean
import io.kotest.common.runBlocking
import io.mockk.coEvery
import io.mockk.coVerify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody
import reactor.core.publisher.Mono
import vvu.study.kotlin.demo.dtos.Device
import vvu.study.kotlin.demo.dtos.utils.DeviceUtils
import vvu.study.kotlin.demo.services.devices.DeviceService

@ExtendWith(SpringExtension::class)
@WebFluxTest(controllers = [DeviceController::class])
internal class DeviceControllerTest {

    @MockkBean
    lateinit var deviceService: DeviceService

    lateinit var webClient: WebTestClient

    @Autowired
    lateinit var controller: DeviceController

    @BeforeEach
    fun setup() {
        webClient = WebTestClient.bindToController(controller).build()
    }

    @Test
    fun createADevice(): Unit = runBlocking {

        val device = DeviceUtils.createADevice()

        coEvery {
            deviceService.create(device)
        } returns device.id

        webClient.post()
            .uri("/devices").contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(Mono.just(device), Device::class.java)
            .exchange().expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id").isNotEmpty
            .jsonPath("$.id").isEqualTo(device.id)

        coVerify(exactly = 1) {
            deviceService.create(device)
        }

    }

    @Test
    fun findOne(): Unit = runBlocking {

        val device = DeviceUtils.createADevice()

        coEvery {
            deviceService.get(device.id)
        } returns device

        webClient.get().uri("/devices/{id}", device.id)
            .accept(MediaType.APPLICATION_JSON)
            .exchange().expectStatus().isOk
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody<Device>().isEqualTo(device)

        coVerify(exactly = 1) {
            deviceService.get(device.id)
        }
    }

}