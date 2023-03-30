package net.kosev.abstractingapi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import net.kosev.abstractingapi.util.TestDispatcherRule
import net.kosev.abstractingapi.util.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val testDispatcherRule = TestDispatcherRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val eventRepo = mock<EventRepository>()
    private val positionRepo = mock<DevicePositionRepository>()
    private val dateFormatter = mock<DateFormatter>()
    private val tested = MainViewModel(eventRepo, positionRepo, dateFormatter)

    @Test
    fun `onScreenLoad with device out of range should load the current event`() = testDispatcherRule.runTest {
        val time = Calendar.getInstance()
        val expected = Event("Name", "Venue", time, 1.0, 2.0)
        whenever(eventRepo.loadCurrentEvent()).thenReturn(expected)
        whenever(dateFormatter.formatDate(time)).thenReturn("Date")
        whenever(positionRepo.loadCurrentPosition()).thenReturn(Position(3.0, 4.0))
        whenever(positionRepo.calculateDistance(3.0, 4.0, 1.0, 2.0))
            .thenReturn(300.0f)

        tested.onScreenLoad()
        assertEquals("Name\nКъде: Venue\nОт: Date\n-", tested.currentEvent.getOrAwaitValue())
    }

    @Test
    fun `onScreenLoad with device in range should load the current event and show near`() = testDispatcherRule.runTest {
        val time = Calendar.getInstance()
        val expected = Event("Name", "Venue", time, 1.0, 2.0)
        whenever(eventRepo.loadCurrentEvent()).thenReturn(expected)
        whenever(dateFormatter.formatDate(time)).thenReturn("Date")
        whenever(positionRepo.loadCurrentPosition()).thenReturn(Position(3.0, 4.0))
        whenever(positionRepo.calculateDistance(3.0, 4.0, 1.0, 2.0))
            .thenReturn(100.0f)

        tested.onScreenLoad()
        assertEquals("Name\nКъде: Venue\nОт: Date\n*** Наблизо съм ***", tested.currentEvent.getOrAwaitValue())
    }
}
