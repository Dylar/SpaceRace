package de.bitb.spacerace

import de.bitb.spacerace.grafik.model.objecthandling.GameImage

sealed class CameraAction {
    sealed class TARGET_ACTION(val targetEntity: GameImage?) : CameraAction() {
        class CAMERA_START(startEntity: GameImage?) : TARGET_ACTION(startEntity)
        class CAMERA_LOCKED(lockedEntity: GameImage) : TARGET_ACTION(lockedEntity)
    }

    object CAMERA_FREE : CameraAction()
}

//enum class CameraActionType {
//    CAMERA_LOCKED, CAMERA_FREE, CAMERA_START;
//}