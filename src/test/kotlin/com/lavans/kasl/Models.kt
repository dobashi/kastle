package com.lavans.kasl

open class A {
    open fun say() = "A: Hello!"
}

class MockedA : A() {
    override fun say() = "MockedA: Yo!"
}