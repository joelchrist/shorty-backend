package nl.joelchrist.shorty.util

import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

fun <T : Any> getLogger(type: KClass<T>) = LoggerFactory.getLogger(type.java)!!