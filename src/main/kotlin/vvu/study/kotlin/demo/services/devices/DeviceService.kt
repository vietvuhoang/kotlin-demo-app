package vvu.study.kotlin.demo.services.devices

import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Service
import vvu.study.kotlin.demo.ds.repos.inf.DeviceRepo
import vvu.study.kotlin.demo.dtos.Device

@Service
class DeviceService(val deviceRepo: DeviceRepo) {
    suspend fun create( device: Device) : String = deviceRepo.create( device ).awaitFirst()
    suspend fun get( id: String) : Device = deviceRepo.findById( id).awaitFirst()
    suspend fun getDevices() : List<Device> = deviceRepo.getDevices().collectList().awaitFirst()
    suspend fun update(device: Device) = deviceRepo.update( device).awaitFirst()
}

@Service
class DeviceEventService {

}