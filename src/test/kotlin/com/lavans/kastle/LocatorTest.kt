package com.lavans.kastle

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

    @Test
    fun `Singleton returns same instance`(){
        val a = Locator.get<A>(A::class)
        val b = Locator.get<A>(A::class)
        val c = Locator.get<A>(A::class)
        val d = Locator.get<A>(A::class)
        println("$a\n$b\n$c\n$d")
        assertEquals(a, b)
        assertEquals(a, c)
        assertEquals(a, d)
    }

    @Test
    fun `Prototype returns different instance`(){
        Locator.put(A::class, type = KClassInfo.Type.Prototype)
        val a = Locator.get<A>(A::class)
        val b = Locator.get<A>(A::class)
        val c = Locator.get<A>(A::class)
        val d = Locator.get<A>(A::class)
        println("$a\n$b\n$c\n$d")
        assertNotEquals(a, b)
        assertNotEquals(a, c)
        assertNotEquals(a, d)
    }
}
