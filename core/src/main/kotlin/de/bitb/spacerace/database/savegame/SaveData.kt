package de.bitb.spacerace.database.savegame

import de.bitb.spacerace.database.converter.PlayerColorConverter
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.model.player.PlayerColor
import de.bitb.spacerace.utils.getDate
import io.objectbox.BoxStore
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne
import java.util.*

@Entity
data class SaveData(
        var name: String = Calendar.getInstance().getDate(),
        @Id
        var uuid: Long = 0,
        @Convert(converter = PlayerColorConverter::class, dbType = String::class)
        var currentColor: PlayerColor = PlayerColor.NONE,
        var loaded: Boolean = false,
        var winAmount: Int = 1 //TODO use me
) {

    @Transient
    @JvmField
    protected var __boxStore: BoxStore? = null

    @JvmField
    var fields: ToMany<FieldData> = ToMany(this, SaveData_.fields)

    @JvmField
    var goal: ToOne<FieldData> = ToOne(this, SaveData_.goal)

    @JvmField
    var players: ToMany<PlayerData> = ToMany(this, SaveData_.players)

}