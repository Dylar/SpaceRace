package de.bitb.spacerace.ui.screens.editor

import de.bitb.spacerace.database.map.FieldConfigData
import de.bitb.spacerace.database.map.NONE_FIELD_CONFIG
import de.bitb.spacerace.grafik.model.space.fields.ConnectionGraphic
import de.bitb.spacerace.grafik.model.space.fields.FieldGraphic
import de.bitb.spacerace.grafik.model.space.groups.ConnectionList
import de.bitb.spacerace.usecase.StreamUseCaseNoParams
import de.bitb.spacerace.usecase.dispender.BehaviourDispenser
import de.bitb.spacerace.usecase.dispender.Dispenser
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton


enum class EditorMode {
    SELECT, CONNECT, EDIT
}

class EditorModeDispenser : BehaviourDispenser<EditorMode> {
    override val publisher: BehaviorSubject<EditorMode> =
            BehaviorSubject.createDefault(EditorMode.SELECT)

    override fun publishUpdate(entity: EditorMode) {
        publisher.onNext(entity)
    }
}

class ObserveEditorModeUseCase @Inject constructor(
        private val editorModeDispenser: EditorModeDispenser
) : StreamUseCaseNoParams<EditorMode> {

    override fun buildUseCaseObservable(): Observable<EditorMode> = editorModeDispenser.publisher

}

class SelectedEntityDispenser : BehaviourDispenser<FieldConfigData> {
    override val publisher: BehaviorSubject<FieldConfigData> =
            BehaviorSubject.createDefault(NONE_FIELD_CONFIG)

    override fun publishUpdate(entity: FieldConfigData) {
        publisher.onNext(entity)
    }
}

class ObserveSelectedEntityUseCase @Inject constructor(
        private val selectedEntityDispenser: SelectedEntityDispenser
) : StreamUseCaseNoParams<FieldConfigData> {

    override fun buildUseCaseObservable(): Observable<FieldConfigData> =
            selectedEntityDispenser.publisher

}

sealed class AddEntity {
    data class AddField(val fieldConfigData: FieldConfigData) : AddEntity()
    data class AddConnection(val field1: FieldConfigData, val field2: FieldConfigData) : AddEntity()
}

@Singleton
class AddEntityDispenser @Inject constructor() : Dispenser<AddEntity> {
    override val publisher: PublishSubject<AddEntity> =
            PublishSubject.create()

    override fun publishUpdate(entity: AddEntity) {
        publisher.onNext(entity)
    }
}

class ObserveAddEntityUseCase @Inject constructor(
        private val addEntityDispenser: AddEntityDispenser
) : StreamUseCaseNoParams<AddEntity> {

    override fun buildUseCaseObservable(): Observable<AddEntity> =
            addEntityDispenser.publisher

}

sealed class AddEntityToMap {
    data class AddFieldToMap(val fieldGraphic: FieldGraphic) : AddEntityToMap()
    data class AddConnectionsToMap(val connectionList: ConnectionList) : AddEntityToMap()
}

@Singleton
class AddEntityToMapDispenser @Inject constructor() : Dispenser<AddEntityToMap> {
    override val publisher: PublishSubject<AddEntityToMap> =
            PublishSubject.create()

    override fun publishUpdate(entity: AddEntityToMap) {
        publisher.onNext(entity)
    }
}

class ObserveAddEntityToMapUseCase @Inject constructor(
        private val addEntityDispenser: AddEntityToMapDispenser
) : StreamUseCaseNoParams<AddEntityToMap> {

    override fun buildUseCaseObservable(): Observable<AddEntityToMap> =
            addEntityDispenser.publisher

}