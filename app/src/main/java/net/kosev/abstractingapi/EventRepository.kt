package net.kosev.abstractingapi

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventRepository @VisibleForTesting constructor(
    private val getCurrentCalendar: () -> Calendar,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    @Inject
    constructor() : this({ Calendar.getInstance() })

    suspend fun loadCurrentEvent(): Event =
        withContext(defaultDispatcher) {
            delay(1_000)
            Event(
                name = "Концерт на Веско Маринов",
                venue = "НДК",
                time = getCurrentCalendar().apply {
                    set(2023, Calendar.MAY, 20, 19, 0)
                },
                latitude = 42.6855522,
                longitude = 23.3167748
            )
        }
}

data class Event(
    val name: String,
    val venue: String,
    val time: Calendar,
    val latitude: Double,
    val longitude: Double
)
