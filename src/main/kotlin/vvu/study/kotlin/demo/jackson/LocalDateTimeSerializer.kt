package vvu.study.kotlin.demo.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

private val ZoneUtc = ZoneOffset.UTC

class LocalDateTimeJsonSerializer : JsonSerializer<LocalDateTime>() {
    override fun serialize(localDateTime: LocalDateTime?, gen: JsonGenerator?, provider: SerializerProvider?) {
        localDateTime?.atZone(ZoneUtc)?.let {
            gen?.writeString(it.toString())
        }
    }
}

class LocalDateTimeJsonDeserializer : JsonDeserializer<LocalDateTime>() {
    override fun deserialize(p0: JsonParser?, p1: DeserializationContext?): LocalDateTime {
        p0?.let {
            return LocalDateTime.parse(p0.text, DateTimeFormatter.ISO_DATE_TIME)
        }?:throw IllegalArgumentException("No JsonParser")
    }

}