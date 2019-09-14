package de.bitb.spacerace.database

import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.map.MapData_
import de.bitb.spacerace.database.player.PlayerData
import io.objectbox.BoxStore
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
data class SaveGame(
        val name: String = "",
        @Id
        var uuid: Long = 0
){

    @Transient
    @JvmField
    protected var __boxStore: BoxStore? = null

    @JvmField
    var fields: ToMany<FieldData> = ToMany(this, SaveGame_.fields)

    @JvmField
    var goal: ToOne<FieldData> = ToOne(this, SaveGame_.goal)

    @JvmField
    var players: ToMany<PlayerData> = ToMany(this, SaveGame_.players)

}