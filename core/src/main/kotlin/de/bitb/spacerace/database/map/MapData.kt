package de.bitb.spacerace.database.map

import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.objecthandling.PositionData
import io.objectbox.BoxStore
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany

@Entity
data class MapData(
        val name: String = "",
        @Id
        var uuid: Long = 0
) {

    @Transient
    @JvmField
    protected var __boxStore: BoxStore? = null

    @JvmField
    var fields: ToMany<FieldData> = ToMany(this, MapData_.fields)

    @JvmField
    var players: ToMany<PlayerData> = ToMany(this, MapData_.players)
}