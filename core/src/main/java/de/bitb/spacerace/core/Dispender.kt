package de.bitb.spacerace.core

import io.reactivex.subjects.BehaviorSubject

interface Dispender<E> {

    val publisher: BehaviorSubject<E>

    fun publishUpdate(entity: E)

}
