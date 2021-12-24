package vvu.study.kotlin.demo.app

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay
import org.junit.jupiter.api.Test
import java.lang.IllegalStateException
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

class DemoAppTest {

   inline fun <T> forEach( list : List<T>, block : (T) -> Unit ) {
       list.forEach {
           block(it)
       }
   }

    @Test
    fun testSuspendFunction() = runBlocking {
        val list = listOf( "Long", "Thac Anh", "Viet")

        try {
            forEach( list = list ) {

                if ( it === "Thac Anh") throw IllegalStateException(it)
                println(it)
                delay(100)
            }
        } catch ( e : Exception) {
            e.printStackTrace()
        }

    }

    @Test
    fun testMain() {
        val mutableList : MutableList<String> = mutableListOf("first", "second")
        // Mutable List allows to add items
        mutableList.add("third")
        println("Mutable List: $mutableList")
        // Mutable List is still a List
        val list : List<String> = mutableList
        println("List: $list")
    }
}