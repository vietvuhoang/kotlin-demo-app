```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-r2dbc</artifactId>
    <version>2.5.2</version>
</dependency>
```

```kotlin
@Configuration
@EnableR2dbcRepositories
class DatastoreConfig : AbstractR2dbcConfiguration() {

    @Value("\${datasource.username}")
    private val userName: String = ""

    @Value("\${datasource.password}")
    private val password: String = ""

    @Value("\${datasource.dbname}")
    private val dbName: String = ""

    @Value("\${datasource.host}")
    private val host: String = ""

    @Value("\${datasource.port}")
    private val port: Int = 5432

    @Bean
    override fun connectionFactory(): ConnectionFactory {
        return PostgresqlConnectionFactory(PostgresqlConnectionConfiguration
            .builder()
            .host(host)
            .port( port)
            .username(userName)
            .password(password).database(dbName).build())
    }
}
```

```kotlin
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
```

```kotlin
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
```

```kotlin
@Configuration
class RouterConfiguration {

    @Bean
    fun pingRoutes(pingHandler: PingHandler) = coRouter {
        GET("/ping", pingHandler::pong)
    }
}

@Component
class PingHandler {
    suspend fun pong(request: ServerRequest): ServerResponse =
        ServerResponse.ok().bodyValueAndAwait(PongMessage())
}
```


```kotlin
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
```