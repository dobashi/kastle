package com.lavans.kastle

import io.vavr.control.Option
import io.vavr.control.Option.none
import java.util.function.Supplier
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
        kclass: KClass<out Any>,
        type: Type = Type.Singleton,
        singletonInstance: Option<Any> = none()
    ) : this(id, kclass.qualifiedName!!, type, singletonInstance) {
        clazz = Option.of(kclass)
    }

    enum class Type { Singleton, Prototype }

    private var clazz: Option<KClass<out Any>> = none()

    fun getKClass(): KClass<out Any> = clazz.getOrElse {
        val kclass = Class.forName(className).kotlin as KClass<out Any>
        clazz = Option.of(kclass)
        kclass
    }

    fun getInstance(): Any = singletonInstance.orElse {
        val instance = getKClass().createInstance()
        println(instance.toString())
        println(instance::class)
        if (type == Type.Singleton) singletonInstance = Option.of(instance)
        Option.of(instance)
    }.get()
}

