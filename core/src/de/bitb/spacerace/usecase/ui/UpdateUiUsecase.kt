package de.bitb.spacerace.usecase.ui

import de.bitb.spacerace.model.player.PlayerColor
import digital.edeka.core.usecase.UseCaseWithoutParams
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class UpdateUiUsecase @Inject constructor()
    : UseCaseWithoutParams<PlayerColor>() {

    private val uiSubject: PublishSubject<PlayerColor> = PublishSubject.create()

    override fun buildUseCaseObservable(): Observable<PlayerColor> {
        return uiSubject
    }

    fun pusblishUpdate(playerColor: PlayerColor) {
        uiSubject.onNext(playerColor)
    }

}