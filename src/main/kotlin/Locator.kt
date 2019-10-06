package com.lavans.kasl

import kotlin.reflect.KClass

object Locator {
    enum class Type { Singleton, Prototype}
    val map = mapOf<KClass<Any>, Any>()
    fun <T>get (klass: KClass<Any>, type: Type = Type.Singleton): T =  map.getOrElse(klass, { create(klass) }) as T
    private fun create(kClass: KClass<Any>): Any {
       // TODO
        return 1;
    }
}