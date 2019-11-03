package de.bitb.spacerace.usecase.game.observe

import de.bitb.spacerace.usecase.StreamUseCaseNoParams
import de.bitb.spacerace.usecase.dispender.*
import io.reactivex.Observable
import javax.inject.Inject

class ObserveRemoveItemUseCase @Inject constructor(
        private val removeItemDispenser: RemoveItemDispenser
) : StreamUseCaseNoParams<RemoveItemConfig> {

    override fun buildUseCaseObservable(): Observable<RemoveItemConfig> = removeItemDispenser.publisher

}

class ObserveAttachItemUseCase @Inject constructor(
        private val attachItemDispenser: AttachItemDispenser
) : StreamUseCaseNoParams<AttachItemConfig> {

    override fun buildUseCaseObservable(): Observable<AttachItemConfig> = attachItemDispenser.publisher

}

class ObserveMoveItemUseCase @Inject constructor(
        private val moveItemDispenser: MoveItemDispenser
) : StreamUseCaseNoParams<List<MoveItemConfig>> {

    override fun buildUseCaseObservable(): Observable<List<MoveItemConfig>> = moveItemDispenser.publisher

}