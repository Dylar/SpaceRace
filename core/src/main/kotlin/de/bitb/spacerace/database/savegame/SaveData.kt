package de.bitb.spacerace.database.savegame

import de.bitb.spacerace.database.converter.PlayerColorConverter
import de.bitb.spacerace.database.map.FieldData
import de.bitb.spacerace.database.player.PlayerData
import de.bitb.spacerace.grafik.model.player.PlayerColor
import de.bitb.spacerace.core.utils.timestampNowDate
import io.objectbox.BoxStore
import io.objectbox.annotation.Convert
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
data class SaveData(
        var name: String = timestampNowDate(),
        @Id
        var uuid: Long = 0,
        var roundCount: Int = 1,
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