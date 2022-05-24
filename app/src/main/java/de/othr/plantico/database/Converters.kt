package de.othr.plantico.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import de.othr.plantico.database.entities.PlantCategory
import java.util.*


class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun StringToPlantCategory(value: String?): List<PlantCategory?>? {
        val gson = Gson()
        val type = object : TypeToken<List<PlantCategory?>?>() {}.type
        return gson.fromJson(value, type)

    }

    @TypeConverter
    fun plantCategoryToString(list: List<PlantCategory?>?): String? {
        val gson = Gson()
        val type = object : TypeToken<List<PlantCategory?>?>() {}.type
        return gson.toJson(list, type)
    }

}