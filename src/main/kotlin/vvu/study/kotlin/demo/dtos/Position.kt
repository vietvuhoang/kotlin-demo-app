package vvu.study.kotlin.demo.dtos

data class Position(val x: Int, val y: Int)

data class Move(val verticalSteps: Int, val horizontalSteps: Int)

infix fun Position.move(step: Move) = this.copy(x = x + step.verticalSteps, y = y + step.horizontalSteps)

operator fun Position.plus(step: Move) = this.move( step )
