package de.othr.plantico.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Plant(
    val plantName: String,
    val plantNameLat: String,
    val family: String,
    val subfamily: String,
    val genus: String,
    val typeSpecies: String,
    val desc: String,
    val propagationDesc: String,
    val wateringDesc: String,
    val difficulty: PlantDifficulty,
    val plantCategory: List<PlantCategory>,
    val wateringCycleDays: Int,
    val wateringLevel: WateringLevel
) {
    @PrimaryKey(autoGenerate = true)
    public var id: Int = 0

}

enum class PlantDifficulty {
    EASY,
    INTERMEDIATE,
    ADVANCED
}

enum class PlantCategory {
    OUTDOOR,
    INDOOR,
    ALL_YEAR,
    SUMMER,
    LOWLIGHT,
    HIGH_LIGHT,
    LOW_MAINTENANCE,
    HIGH_MAINTENANCE,
    PET_FRIENDLY,
    DURABLE,
    FLOWERING,
    WINTER,
    ANNUAL
}

enum class WateringLevel {
    LOW,
    MEDIUM,
    HIGH
}


