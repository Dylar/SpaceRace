package de.bitb.spacerace.usecase.dispender

import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.Subject

interface Dispenser<E> {

    val publisher: Subject<E>

    fun publishUpdate(entity: E)

}

interface BehaviourDispenser<E> : Dispenser<E>{

    override val publisher: BehaviorSubject<E>

    fun value(): E = publisher.value

}
