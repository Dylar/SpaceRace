package de.bitb.spacerace

import com.badlogic.gdx.Gdx
import de.bitb.spacerace.Logger.Constants.PACKAGE_NAME

object Logger {

    private const val JAVA_EXTENSION = ".java"
    private const val KOTLIN_EXTENSION = ".kt"
    private const val isAllowedToLog: Boolean = true

    private val callingClass: String
        get() = findCallingClass()

    private fun findCallingClass(): String {
        val isLoggerClass = fun(stackTraceElement: StackTraceElement): Boolean {
            return stackTraceElement.className == Logger::class.java.canonicalName
        }

        val stackTraceElements = Thread.currentThread().stackTrace
        for (searchLoggerClassIndex in stackTraceElements.indices) {
            if (isLoggerClass(stackTraceElements[searchLoggerClassIndex])) {
                try {
                    for (searchNextNotLoggerClassIndex in searchLoggerClassIndex until stackTraceElements.size) {
                        val callingClass = stackTraceElements[searchNextNotLoggerClassIndex]
                        if (!isLoggerClass(callingClass)) {
                            val split = callingClass.className.split("\\.".toRegex())
                                    .dropLastWhile { it.isEmpty() }.toTypedArray()
                            var className = split.last()

                            if (className.contains("$")) {
                                className = className.split("\\$".toRegex())
                                        .dropLastWhile { it.isEmpty() }.toTypedArray()[0]
                            }
                            val isJava = callingClass.fileName.endsWith(JAVA_EXTENSION)
                            return "($className${if (isJava) JAVA_EXTENSION else KOTLIN_EXTENSION}:${callingClass.lineNumber})"
                        }
                    }
                } catch (e: Exception) {
                    return Logger::class.java.simpleName
                }

            }
        }
        return Logger::class.java.simpleName
    }


//    fun error(message: String) {
//        if (isAllowedToLog) {
//            Log.e(callingClass, message)
//        }
//    }
//
//    fun debug(message: String) {
//        if (isAllowedToLog) {
//            Log.d(callingClass, message)
//        }
//    }
//
//    fun info(message: String) {
//        if (isAllowedToLog) {
//            Log.i(callingClass, message)
//        }
//    }
//
//    fun verbose(message: String) {
//        if (isAllowedToLog) {
//            Log.v(callingClass, message)
//        }
//    }
//
//    fun warn(message: String) {
//        if (isAllowedToLog) {
//            Log.w(callingClass, message)
//        }
//    }

    fun println(message: String) {
        if (isAllowedToLog) {
//            System.out.println("$callingClass: $message")
//            Gdx.app.log(callingClass, createMessage(message))
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

    private var time: Long = 0
    fun startTimer() {
        time = System.currentTimeMillis()
    }

    fun printTimer(msg: String) {
        val inMillis = (System.currentTimeMillis() - time).toDouble()
        println("$msg (TIME: $inMillis)")
    }


    object Constants {
        const val PACKAGE_NAME = "bitb"
    }

    fun log(vararg params: String) {
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

                    Gdx.app.log(paramsString, "$last called by $callerStack\n")
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

    private fun appClass(): (StackTraceElement) -> Boolean = { it.className.contains(PACKAGE_NAME) }


//    fun wtf(message: String) {
//        if (isAllowedToLog) {
//            Log.wtf(callingClass, message)
//        }
//    }
}