package de.othr.plantico.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    indices = [Index("plantID")], foreignKeys = [
        ForeignKey(
            entity = Plant::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("plantID"),
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class OwnedPlant(
    val plantName: String,
    val plantID: Int,
    val lastWatered: Date,
    val birthday: Date?,
    val customWateringCycle: Int,
    val location: String?
) {
    @PrimaryKey(autoGenerate = true)
    public var id: Int = 0

}
