package vvu.study.kotlin.demo.exceptions

open class NotFoundException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(throwable : Throwable) : super(throwable)
}

