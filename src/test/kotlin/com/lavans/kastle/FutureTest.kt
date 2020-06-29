package com.lavans.kastle

import io.vavr.concurrent.Future
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class FutureTest {

    @Test
    fun `A says Hello!`() {
        val f = Future.of {
            1
        }
        println(f.get())
    }
}
