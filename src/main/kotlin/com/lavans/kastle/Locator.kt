package com.lavans.kastle

import kotlin.reflect.KClass
import com.lavans.kastle.KClassInfo.Type

typealias Klass = KClass<out Any>

fun Klass.name(): String = qualifiedName!!

object Locator {
    val map = mutableMapOf<String, KClassInfo>()

    inline fun <reified T> get(id: String): T = map.getOrPut(id) { KClassInfo(id) }.getInstance() as T
    inline fun <reified T> get(): T = get(T::class.qualifiedName!!)

    fun put(id: String, className: String, type: Type = Type.Singleton): KClassInfo? = map.put(id, KClassInfo(id, className, type))
    fun put(id: String, klass: Klass, type: Type = Type.Singleton): KClassInfo? = map.put(id, KClassInfo(id, klass, type))
    fun put(idClass: Klass, klass: Klass = idClass, type: Type = Type.Singleton) = put(idClass.name(), klass, type)

    fun put(idClass: Klass, instance: Any) = map.put(idClass.name(), KClassInfo(idClass.name(), singletonInstance = instance.option()))
}
