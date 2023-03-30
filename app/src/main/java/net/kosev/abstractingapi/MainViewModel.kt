package net.kosev.abstractingapi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val eventRepository: EventRepository,
    private val positionRepository: DevicePositionRepository,
    private val dateFormatter: DateFormatter
) : ViewModel() {

    private val _currentEvent = MutableLiveData<String>()
    val currentEvent: LiveData<String> = _currentEvent

    fun onScreenLoad() {
        viewModelScope.launch {
            val event = eventRepository.loadCurrentEvent()
            val position = positionRepository.loadCurrentPosition()
            val distance = positionRepository.calculateDistance(
                position.latitude, position.longitude,
                event.latitude, event.longitude
            )

            val isNear = if (distance < 200.0f) {
                "\n*** Наблизо съм ***"
            } else {
                "\n-"
            }
            val date = dateFormatter.formatDate(event.time)
            _currentEvent.value = "${event.name}\nКъде: ${event.venue}\nОт: $date$isNear"
        }
    }
}
