package de.bitb.spacerace

import com.badlogic.gdx.Gdx
import java.util.*

const val PACKAGE_NAME = "bitb"

object Logger {
    private const val isAllowedToLog: Boolean = true

    private fun appClass(): (StackTraceElement) -> Boolean = { it.className.contains(PACKAGE_NAME) }

    private var time: Long = 0
    fun startTimer() {
        time = System.currentTimeMillis()
    }

    fun printTimer(msg: String) {
        val inMillis = (System.currentTimeMillis() - time).toDouble()
        println("$msg (TIME: $inMillis)")
    }

    fun <TYPE : Any> println(vararg params: TYPE) {
        if (isAllowedToLog) {
            log(params)
        }
    }

    private fun createMessage(message: String, printTime: Boolean = false, printThread: Boolean = true): String {
        val timeString = if (printTime) {
            val inMillis = (System.currentTimeMillis() - time).toDouble()
            "(TIME: $inMillis) "
        } else ""
        val threadString = if (printThread) " (THREAD: ${Thread.currentThread().name}" else ""
        return "$timeString<-- $message --> $threadString)"
    }

    private fun <TYPE : Any> log(vararg params: TYPE) {
        Pair(Thread.currentThread(), appClass())
                .also { (thread, filterClass) ->
                    val callerStack = thread
                            .stackTrace
                            .filter(filterClass)
                            .drop(2)
                            .map { "$it" }
                            .map { it.filterPackage() }
                            .map {
                                "\n$it"
                            }

                    val last = thread
                            .stackTrace
                            .filter(filterClass)
                            .drop(2)
                            .first()
                            .let { "$it" }
                            .filterPackage()
                            .filterArrays()

                    val threadString = "Thread: ${thread.name}\n"

                    val paramsString = params
                            .let { Arrays.deepToString(it) }
                            .let { "Params: $it" }

                    val tag = "$threadString$paramsString"
                            .filterArrays()
                    val message = "\nCaller: $last\nStack: $callerStack\n"

                    Gdx.app.log(tag, message)
                }
    }

    private fun String.filterArrays(): String =
            replace("[", "")
                    .replace("]", "")
}

fun String.filterPackage(): String {
    return replaceBefore(".", "")
            .substring(1)
            .replaceBefore(".", "")
            .substring(1)
            .replaceBefore(".", "")
            .substring(1)
}
