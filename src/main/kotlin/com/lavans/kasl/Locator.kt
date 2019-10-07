package com.lavans.kasl

import kotlin.reflect.KClass

object Locator {
    private val map = mutableMapOf<String, KClassInfo>()
//    fun get(id: String)  = map.getOrPut(id) { KClassInfo(id) }.getInstance()
    fun <T> get(id: String): T = map.getOrPut(id) { KClassInfo(id) }.getInstance() as T
    fun <T> get(kclass: KClass<out Any>): T = get(kclass.qualifiedName!!)
    fun put(id: String, className: String): KClassInfo? = map.put(id, KClassInfo(id, className))
    fun put(id: String, kclass: KClass<out Any> ): KClassInfo? = map.put(id, KClassInfo(id, kclass))
    fun put(idClass: KClass<out Any>, kclass: KClass<out Any> ) = put(idClass.qualifiedName!!, kclass)
    fun put(idClass: KClass<out Any>, className: String) = put(idClass.qualifiedName!!, className)
}