package vvu.study.kotlin.demo.config

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

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