package vvu.study.kotlin.demo.dtos

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class PositionKtTest {

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun testMove() {
        val position = Position( x = 4, y = 3)
        val step = Move( verticalSteps = 5, horizontalSteps = 7)

        val destination = position move step

        println("Destination : $destination")

        destination shouldBe Position( x = position.x + step.verticalSteps, y = position.y + step.horizontalSteps)
    }

    @Test
    fun testPlus() {
        val position = Position( x = 4, y = 3)
        val step = Move( verticalSteps = 5, horizontalSteps = 7)

        val destination = position + step

        println("Destination : $destination")

        destination shouldBe Position( x = position.x + step.verticalSteps, y = position.y + step.horizontalSteps)
    }

    @Test
    fun testF() {

    }

    fun main() {
        val mutableList: MutableList<String> = mutableListOf("first", "second")
        // Mutable List allows to add item
        mutableList.add("third")
        println("String list : $mutableList")
        // MutableList is still a List
        val list : List<String> = mutableList
        println("String list : $list")
    }
}