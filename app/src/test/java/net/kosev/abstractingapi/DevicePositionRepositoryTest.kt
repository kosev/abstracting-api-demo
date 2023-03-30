package net.kosev.abstractingapi

import android.location.Location
import net.kosev.abstractingapi.util.TestDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class DevicePositionRepositoryTest {

    @get:Rule
    val testCoroutineRule = TestDispatcherRule()

    private val locationServices = mock<LocationServicesWrapper>()
    private val tested = DevicePositionRepository(locationServices)

    @Test
    fun `loadCurrentPosition should read location from location services`() = testCoroutineRule.runTest {
        val location = mock<Location>()
        whenever(location.latitude).thenReturn(1.0)
        whenever(location.longitude).thenReturn(2.0)
        whenever(locationServices.getLastLocation()).thenReturn(location)

        val actual = tested.loadCurrentPosition()

        val expected = Position(1.0, 2.0)
        assertEquals(expected, actual)
    }
}
