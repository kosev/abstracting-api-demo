package net.kosev.abstractingapi

import net.kosev.abstractingapi.util.TestDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.util.*

class EventRepositoryTest {

    @get:Rule
    val testCoroutineRule = TestDispatcherRule()

    @Test
    fun `loadCurrentEvent should return hardcoded event`() = testCoroutineRule.runTest {
        val calendar = Calendar.getInstance().apply {
            set(2023, Calendar.MAY, 20, 19, 0)
        }
        val expected = Event(
            name = "Концерт на Веско Маринов",
            venue = "НДК",
            time = calendar,
            latitude = 42.6855522,
            longitude = 23.3167748
        )
        val tested = EventRepository({ calendar }, testCoroutineRule.dispatcher)

        assertEquals(expected, tested.loadCurrentEvent())
    }
}
