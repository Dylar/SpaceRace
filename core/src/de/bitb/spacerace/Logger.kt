package de.bitb.spacerace

import com.badlogic.gdx.Gdx

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

    fun println(message: String) {
        if (isAllowedToLog) {
            log(message)
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

    fun log(vararg params: Any) {
        Pair(Thread.currentThread(), appClass())
                .also { (thread, filterClass) ->
                    val callerStack = thread
                            .stackTrace
                            .filter(filterClass)
                            .drop(2)
                            .map { "$it" }
                            .map { it.filterPackage() }
                            .map { "\n$it" }

                    val last = thread
                            .stackTrace
                            .filter(filterClass)
                            .drop(2)
                            .first()
                            .toString()
                            .filterPackage()

                    val paramsString = params
                            .map { it }
                            .let {
                                if (it.isEmpty()) "Log"
                                else "Params: $it"
                            }
                            .let { "Thread: ${thread.name}\n$it" }

                    Gdx.app.log(paramsString, "\n$last\ncalled by $callerStack\n")
                }
    }

    private fun String.filterPackage(): String {
        return replaceBefore(".", "")
                .substring(1)
                .replaceBefore(".", "")
                .substring(1)
                .replaceBefore(".", "")
                .substring(1)
    }
}