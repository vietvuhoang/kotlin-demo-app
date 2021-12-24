package vvu.study.kotlin.demo.ds.repos.inf
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import vvu.study.kotlin.demo.dtos.Device

interface DeviceRepo {
    fun getDevices(): Flux<Device>
    fun findById(id: String) : Mono<Device>
    fun create(device: Device): Mono<String>
    fun update(device: Device): Mono<Unit>
}