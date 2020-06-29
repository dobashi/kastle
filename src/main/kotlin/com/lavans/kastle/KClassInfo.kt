package com.lavans.kastle

import io.vavr.control.Option
import io.vavr.control.Option.none
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

class KClassInfo(
    private val id: String,
    private val className: String = id,
    private val type: Type = Type.Singleton,
//    val initMethod: Option<String> = none(),
    private var singletonInstance: Option<Any> = none()
) {
    constructor(
        id: String,
        klass: KClass<out Any>,
        type: Type = Type.Singleton,
        singletonInstance: Option<Any> = none()
    ) : this(id, klass.qualifiedName!!, type, singletonInstance) {
        clazz = Option.of(klass)
    }

    enum class Type { Singleton, Prototype }

    fun getInstance(): Any = singletonInstance.orElse {
        val instance = getKClass().createInstance()
        if (type == Type.Singleton) singletonInstance = Option.of(instance)
        Option.of(instance)
    }.get()

    private var clazz: Option<KClass<out Any>> = none()

    private fun getKClass(): KClass<out Any> = clazz.getOrElse {
        val kclass = Class.forName(className).kotlin as KClass<out Any>
        if (kclass.objectInstance != null) throw IllegalArgumentException("$className must be class, but it is object.")
        clazz = Option.of(kclass)
        kclass
    }
}

