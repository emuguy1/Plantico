package de.othr.plantico.database

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PlantApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { PlanticoDB.getDatabase(this, applicationScope) }
    val repository by lazy { Repository(database.plantDAO()) }
}
