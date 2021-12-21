package vvu.study.kotlin.demo.controllers

import org.springframework.web.bind.annotation.*
import vvu.study.kotlin.demo.dtos.Device
import vvu.study.kotlin.demo.services.devices.DeviceService

data class DeviceResponse( val id: String)

@RestController
@RequestMapping("/devices")
class DeviceController(val deviceService: DeviceService) {

    @PostMapping
    suspend fun create(@RequestBody device: Device) : DeviceResponse {
        return DeviceResponse( id = deviceService.create(device))
    }

    @GetMapping("/{id}")
    suspend fun findOne( @PathVariable id: String) : Device = deviceService.get(id)

}
