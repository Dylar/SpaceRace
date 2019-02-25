package de.bitb.spacerace

import com.badlogic.gdx.Gdx

object Logger {

    private const val isAllowedToLog: Boolean = true

    private val callingClass: String
        get() {
            val stackTraceElements = Thread.currentThread().stackTrace
            for (searchLoggerClassIndex in stackTraceElements.indices) {
                if (isLoggerClass(stackTraceElements[searchLoggerClassIndex])) {
                    try {
                        for (searchNextNotLoggerClassIndex in searchLoggerClassIndex until stackTraceElements.size) {
                            val callingClass = stackTraceElements[searchNextNotLoggerClassIndex]
                            if (!isLoggerClass(callingClass)) {
                                val split = callingClass.className.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                                var className = split.last()

                                if (className.contains("$")) {
                                    className = className.split("\\$".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
                                }

                                return "(" + className + ".java:" + callingClass.lineNumber + ")"
                            }
                        }
                    } catch (e: Exception) {
                        return Logger::class.java.simpleName
                    }

                }
            }
            return Logger::class.java.simpleName
        }

    private fun isLoggerClass(stackTraceElement: StackTraceElement): Boolean {
        return stackTraceElement.className == Logger::class.java.canonicalName
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
            Gdx.app.log(callingClass,message)
        }
    }

    private var time: Long = 0
    fun startTimer() {
        time = System.currentTimeMillis()
    }

    fun printTimer(msg: String) {
        val inMillis = (System.currentTimeMillis() - time).toDouble()
        println("$msg (TIME: $inMillis)")
    }

//    fun wtf(message: String) {
//        if (isAllowedToLog) {
//            Log.wtf(callingClass, message)
//        }
//    }
}