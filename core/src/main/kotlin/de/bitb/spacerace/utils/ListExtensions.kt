package de.bitb.spacerace.utils

fun <T> List<T>.doForEachExceptLast(
    executeForAll: (T) -> Unit,
    executeForAllExceptLast: (T) -> Unit,
    beforeSharedExecution: Boolean = false
) {
    doForEachExceptPosition(executeForAll, executeForAllExceptLast, beforeSharedExecution, lastIndex)
}

fun <T> List<T>.doForEachExceptFirst(
    executeForAll: (T) -> Unit,
    executeForAllExceptFirst: (T) -> Unit,
    beforeSharedExecution: Boolean = true
) {
    doForEachExceptPosition(executeForAll, executeForAllExceptFirst, beforeSharedExecution, 0)
}

fun <T> List<T>.doForEachExceptPosition(
    executeForAll: (T) -> Unit,
    executeForAllExceptPosition: (T) -> Unit,
    beforeSharedExecution: Boolean,
    vararg excludedPosition: Int
) {
    for (obj in this.withIndex()) {
        fun doBefore(execute: Boolean) {
            if (execute && !excludedPosition.contains(obj.index)) {
                executeForAllExceptPosition(obj.value)
            }
        }

        doBefore(beforeSharedExecution)
        executeForAll(obj.value)
        doBefore(!beforeSharedExecution)
    }
}
