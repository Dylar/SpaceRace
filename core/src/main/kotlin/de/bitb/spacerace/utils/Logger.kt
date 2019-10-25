package de.bitb.spacerace.utils

import com.badlogic.gdx.Gdx

const val PACKAGE_NAME = "de.bitb."
const val LOG_BORDER_TOP: String = "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"
const val LOG_BORDER_BOT: String = "--------------------------------------------------------------------------------------"

object Logger {
    val isAllowedToLog: Boolean = true

    private fun appClass(): (StackTraceElement) -> Boolean = { it.className.contains(PACKAGE_NAME) }

    private var time: Long = 0

    fun startTimer() {
        time = System.currentTimeMillis()
    }

    fun printTimer(msg: String) {
        val inMillis = (System.currentTimeMillis() - time).toDouble()
        printLog("$msg (TIME: $inMillis)")
    }

    fun <TYPE : Any> printLog(vararg params: TYPE) {
        if (isAllowedToLog) {
            val log = createLog(params)
            printMessage(LOG_BORDER_TOP +
                    "\n${log.timeStamp}" +
                    "\n${log.params}",
                    "\n${log.callerClass}" +
                            "\n${log.thread}" +
                            "\n${log.callerStack}" +
                            "\n$LOG_BORDER_BOT")
        }
    }

    fun simplePrint(message: String) {
        val log = createLog(message)
        val tag = LOG_BORDER_TOP +
                "\n${log.timeStamp}" +
                "\nDEBUG:"
        val msg = "\n${log.params}" +
                "\n${log.callerClass}" +
                "\n$LOG_BORDER_BOT"
        printMessage(tag, msg)
    }

    fun justPrint(msg: String) {
        printMessage("JUST PRINT", msg)
    }

    private fun printMessage(tag: String, message: String) {
        Gdx.app?.let {
            Gdx.app.log(tag, message)
        } ?: kotlin.io.println("$tag $message")
    }

    private fun <TYPE : Any> createLog(vararg params: TYPE): LogData {
        val paramsString = params
                .contentDeepToString()
                .removeArrayBrackets()
                .let { "PARAMS: $it" }

        val thread = Thread.currentThread()
        val threadString = thread.name
                .removeArrayBrackets()
                .let { "THREAD: $it" }

        val filterClass = appClass()
        val stackList = thread
                .stackTrace
                .filter(filterClass)
                .asSequence()

        val callerClass = stackList
                .drop(2)
                .first()
                .let { "$it" }
                .removePackage()
                .removeArrayBrackets()
                .let { "CALLER: $it" }

        val callerStack = stackList
                .drop(3)
                .map { "\n${it.toString().removePackage()}" }
                .toList()
                .toTypedArray()
                .contentDeepToString()
                .removePackage()
                .removeArrayBrackets()
                .let { "STACK:$it" }

        return LogData(threadString, paramsString, callerClass, callerStack)
    }
}

data class LogData(
        val thread: String,
        val params: String,
        val callerClass: String,
        val callerStack: String,
        val timeStamp: String = "TIME: ${timestampNow()}"
)

fun String.removePackage(): String = replace(PACKAGE_NAME, "")
fun String.removeArrayBrackets(): String {
    val startBracket = "["
    val endBracket = "]"
    var result = this
    while (result.contains(startBracket) && result.contains(endBracket)) {
        result = result
                .replace(startBracket, "")
                .replace(endBracket, "")
    }

    return result
}
