package vvu.study.kotlin.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("vvu.study.kotlin.demo")
class DemoApiApplication {
}

fun main(args: Array<String>) {
    runApplication<DemoApiApplication>(*args)
}
