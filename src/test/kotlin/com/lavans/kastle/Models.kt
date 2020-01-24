package com.lavans.kastle

open class A {
    open fun say() = "A: Hello!"
}

class MockedA : A() {
    override fun say() = "MockedA: Yo!"
}