package de.bitb.spacerace.model

abstract class DataObject {

    abstract var uuid: Long

//    override fun equals(other: Any?): Boolean {
//        return if (other is DataObject) other.uuid == uuid else false
//    }
//
//    override fun hashCode(): Int {
//        return (super.hashCode() + uuid).toInt()
//    }
}
