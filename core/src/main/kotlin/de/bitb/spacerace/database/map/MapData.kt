package de.bitb.spacerace.database.map

import de.bitb.spacerace.database.converter.PositionDataConverter
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.objecthandling.PositionData
import io.objectbox.BoxStore
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
data class MapData(
        val name: String = "",
        @Id
        var uuid: Long = 0,
        @Convert(converter = PositionDataConverter::class, dbType = String::class)
        var startPosition: PositionData = PositionData()
){


    @Transient
    @JvmField
    protected var __boxStore: BoxStore? = null

//    @JvmField
//    var fields: ToMany<FieldData> = ToMany(this, MapData_.fields)
//
//    @JvmField
//    var goal: ToOne<FieldData> = ToOne(this, MapData_.goal)
//
//    @JvmField
//    var players: ToMany<PlayerData> = ToMany(this, MapData_.players)

    @Transient
    @JvmField
    var fields: MutableList<FieldData> = mutableListOf()
    @Transient
    @JvmField
    var goal: FieldData = FieldData()
    @Transient
    @JvmField
    var players: MutableList<PlayerData> =  mutableListOf()

}