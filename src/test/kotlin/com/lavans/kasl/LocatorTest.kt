package com.lavans.kasl

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class LocatorTest {

    @Test
    fun `A says Hello!`() {
        val a = Locator.get<A>(A::class)
        println(a.say())
        assertEquals("A: Hello!", a.say())
    }

    @Test
    fun `MockedA says Yo!`() {
        Locator.put(A::class, MockedA::class)
        val a = Locator.get<A>(A::class)
        println(a.say())
        assertTrue(a.say().contains("Yo"))
    }
}