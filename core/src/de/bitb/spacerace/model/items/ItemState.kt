package de.bitb.spacerace.model.items

enum class ItemState {
    STORAGE,
    USED,
    EQUIPPED,
    DISPOSED,
    ATTACHED,

    NONE;

    fun isStorage(): Boolean {
        return this == STORAGE
    }

    fun isUsed(): Boolean {
        return this == USED
    }

    fun isEquipped(): Boolean {
        return this == EQUIPPED
    }

    fun isDisposed(): Boolean {
        return this == DISPOSED
    }

    fun isAttached(): Boolean {
        return this == ATTACHED
    }
}