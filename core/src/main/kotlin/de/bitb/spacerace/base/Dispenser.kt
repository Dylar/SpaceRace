package de.bitb.spacerace.base

import io.reactivex.subjects.BehaviorSubject

interface Dispenser<E> {

    val publisher: BehaviorSubject<E>

    fun publishUpdate(entity: E)

}
