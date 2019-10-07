package com.lavans.kasl

import kotlin.reflect.KClass
import com.lavans.kasl.KClassInfo.Type

typealias Klass = KClass<out Any>
fun Klass.name(): String = qualifiedName!!

object Locator {
    private val map = mutableMapOf<String, KClassInfo>()
    //    fun get(id: String)  = map.getOrPut(id) { KClassInfo(id) }.getInstance()
    fun <T> get(id: String): T = map.getOrPut(id) { KClassInfo(id) }.getInstance() as T
    fun <T> get(klass: Klass): T = get(klass.name())

    fun put(id: String, className: String, type: Type = Type.Singleton): KClassInfo? = map.put(id, KClassInfo(id, className, type))
    fun put(id: String, klass: Klass, type: Type = Type.Singleton): KClassInfo? = map.put(id, KClassInfo(id, klass, type))
    fun put(idClass: Klass, klass: Klass = idClass, type: Type = Type.Singleton) = put(idClass.name(), klass, type)
//    fun put(idClass: Klass, className: String = idClass.name(), type: Type = Type.Singleton) = put(idClass.name(), className, type)
}