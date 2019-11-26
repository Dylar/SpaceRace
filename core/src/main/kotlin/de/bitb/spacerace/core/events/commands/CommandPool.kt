package de.bitb.spacerace.core.events.commands

import java.util.*
import kotlin.reflect.KClass

object CommandPool {
    private val pool = mutableMapOf<String, Queue<BaseCommand>>()

    fun <T : BaseCommand> getCommand(commandClass: KClass<T>): T =
            (pool[commandClass.simpleName]?.poll() as? T?)
                    ?: commandClass.java.newInstance()

    fun <T : BaseCommand> addPool(command: T) {
        val commandClass = command::class.simpleName!!
        val poolList = pool[commandClass]
                ?: LinkedList<BaseCommand>().also { pool[commandClass] = it }
        poolList.add(command)
    }

}

//interface Pool<OBJECT> {
//    val pool: Queue<OBJECT>
//    fun create(): OBJECT
//    fun get(): OBJECT = pool.poll() ?: create()
//
//    fun reset(obj: OBJECT) = pool.add()
//
//}
//interface Poolable{
//    reset
//}