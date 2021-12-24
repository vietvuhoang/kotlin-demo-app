package vvu.study.kotlin.demo.ds.repos.impl

import org.springframework.data.annotation.Id
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.data.relational.core.query.Update
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty
import vvu.study.kotlin.demo.ds.repos.inf.DeviceRepo
import vvu.study.kotlin.demo.dtos.Device
import vvu.study.kotlin.demo.exceptions.NotFoundException
import java.time.LocalDateTime

data class DeviceEntity (
    @Id
    var id: String,
    val productionCode : String,
    val serialNumber: String,
    val deviceType: String,
    val registrationDate : LocalDateTime
)

fun DeviceEntity.toDto() = Device(
    id = this.id,
    productionCode = this.productionCode,
    serialNumber = this.serialNumber,
    deviceType = this.deviceType,
    registrationDate = this.registrationDate )

fun Device.toEntity() =  DeviceEntity(
    id = this.id,
    productionCode = this.productionCode,
    serialNumber = this.serialNumber,
    deviceType = this.deviceType,
    registrationDate = this.registrationDate )

class DeviceNotFoundException : NotFoundException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(throwable : Throwable) : super(throwable)
}

@Repository
class DeviceRepoImpl(private val template : R2dbcEntityTemplate) : DeviceRepo {

    private companion object {
        const val TABLE_NAME  = "devices"
    }

    override fun findById(id: String): Mono<Device> = template.select( DeviceEntity::class.java)
        .from(TABLE_NAME)
        .matching(Query.query(Criteria.from(Criteria.where("id").`is`(id))))
        .one()
        .map { it.toDto() }.switchIfEmpty { Mono.error(DeviceNotFoundException(id)) }

    override fun getDevices(): Flux<Device> = template.select( Device::class.java).from(TABLE_NAME).all()

    override fun create(device: Device): Mono<String> =
        template
            .insert(DeviceEntity::class.java)
            .into(TABLE_NAME)
            .using(device.toEntity())
            .map { it.id }

    override fun update(device: Device) : Mono<Unit> {
        return template.update(DeviceEntity::class.java)
            .inTable(TABLE_NAME)
            .matching(Query.query(Criteria.where("id").`is`(device.id)))
            .apply(
                Update.update("device_type", device.deviceType)
                .set("production_code", device.productionCode )).map {  }
    }

}