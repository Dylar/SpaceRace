package de.bitb.spacerace.usecase.game.observe

import de.bitb.spacerace.usecase.StreamUseCaseNoParams
import de.bitb.spacerace.usecase.dispender.RemoveItemConfig
import de.bitb.spacerace.usecase.dispender.RemoveItemDispenser
import io.reactivex.Observable
import javax.inject.Inject

class ObserveRemoveItemUseCase @Inject constructor(
        private val removeItemDispenser: RemoveItemDispenser
) : StreamUseCaseNoParams<RemoveItemConfig> {

    override fun buildUseCaseObservable(): Observable<RemoveItemConfig> = removeItemDispenser.publisher

}