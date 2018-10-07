package de.bitb.spacerace

import android.util.Log

object Logger {

    private val isAllowedToLog: Boolean
        get() = true

    private val callingClass: String
        get() {
            val stackTraceElements = Thread.currentThread().stackTrace
            for (i in stackTraceElements.indices) {
                if (stackTraceElements[i].className == Logger::class.java.canonicalName) {
                    return try {
                        val split = stackTraceElements[i + 2].className.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        var className = split[split.size - 1]

                        if (className.contains("$")) {
                            className = className.split("\\$".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
                        }

                        "(" + className + ".java:" + stackTraceElements[i + 2].lineNumber + ")"
                    } catch (e: Exception) {
                        Logger::class.java.simpleName
                    }

                }
            }
            return Logger::class.java.simpleName
        }

    fun error(message: String) {
        if (isAllowedToLog) {
            Log.e(callingClass, message)
        }
    }

    fun debug(message: String) {
        if (isAllowedToLog) {
            Log.d(callingClass, message)
        }
    }

    fun info(message: String) {
        if (isAllowedToLog) {
            Log.i(callingClass, message)
        }
    }

    fun verbose(message: String) {
        if (isAllowedToLog) {
            Log.v(callingClass, message)
        }
    }

    fun warn(message: String) {
        if (isAllowedToLog) {
            Log.w(callingClass, message)
        }
    }

    fun println(message: String) {
        if (isAllowedToLog) {
            System.out.println("$callingClass: $message")
        }
    }
//    fun wtf(message: String) {
//        if (isAllowedToLog) {
//            Log.wtf(callingClass, message)
//        }
//    }
}