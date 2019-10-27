package de.bitb.spacerace.usecase.dispender

import io.reactivex.subjects.BehaviorSubject

interface Dispenser<E> {

    val publisher: BehaviorSubject<E>

    fun publishUpdate(entity: E)

}
