package vvu.study.kotlin.demo.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer
import java.util.*


@Configuration
@EnableWebFlux
class WebFluxConfig : WebFluxConfigurer {
    @Autowired
    fun configureJackson(objectMapper: ObjectMapper) {
        objectMapper.setTimeZone( TimeZone.getTimeZone("UTC"))
        objectMapper.registerModule(JavaTimeModule()).registerModule(KotlinModule())
    }

}