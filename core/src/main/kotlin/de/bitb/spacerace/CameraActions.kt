package de.bitb.spacerace

enum class CameraActions {
    CAMERA_LOCKED, CAMERA_FREE;

    fun isFree(): Boolean {
        return this == CAMERA_FREE
    }
}