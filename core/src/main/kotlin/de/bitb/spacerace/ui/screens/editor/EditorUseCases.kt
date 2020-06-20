package de.bitb.spacerace.ui.screens.editor

import de.bitb.spacerace.database.map.FieldConfigData
import de.bitb.spacerace.database.map.NONE_FIELD_CONFIG
import de.bitb.spacerace.usecase.StreamUseCaseNoParams
import de.bitb.spacerace.usecase.dispender.Dispenser
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject


enum class EditorMode {
    SELECT, PLACE, EDIT, DRAG
}

class EditorModeDispenser : Dispenser<EditorMode> {
    override val publisher: BehaviorSubject<EditorMode> = BehaviorSubject.create()

    override fun publishUpdate(entity: EditorMode) {
        publisher.onNext(entity)
    }
}

class ObserveEditorModeUseCase @Inject constructor(
        private val editorModeDispenser: EditorModeDispenser
) : StreamUseCaseNoParams<EditorMode> {

    override fun buildUseCaseObservable(): Observable<EditorMode> = editorModeDispenser.publisher.doAfterNext {
    }

}

class SelectedEntityDispenser : Dispenser<FieldConfigData> {
    override val publisher: BehaviorSubject<FieldConfigData> = BehaviorSubject.createDefault(NONE_FIELD_CONFIG)

    override fun publishUpdate(entity: FieldConfigData) {
        publisher.onNext(entity)
    }
}

class ObserveSelectedEntityUseCase @Inject constructor(
        private val selectedEntityDispenser: SelectedEntityDispenser
) : StreamUseCaseNoParams<FieldConfigData> {

    override fun buildUseCaseObservable(): Observable<FieldConfigData> = selectedEntityDispenser.publisher

}