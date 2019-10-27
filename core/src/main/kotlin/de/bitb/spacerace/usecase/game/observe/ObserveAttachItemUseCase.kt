package de.bitb.spacerace.usecase.game.observe

import de.bitb.spacerace.core.AttachItemConfig
import de.bitb.spacerace.core.AttachItemDispenser
import de.bitb.spacerace.usecase.StreamUseCaseNoParams
import io.reactivex.Observable
import javax.inject.Inject

class ObserveAttachItemUseCase @Inject constructor(
        private val attachItemDispenser: AttachItemDispenser
) : StreamUseCaseNoParams<AttachItemConfig> {

    override fun buildUseCaseObservable(): Observable<AttachItemConfig> = attachItemDispenser.publisher

}