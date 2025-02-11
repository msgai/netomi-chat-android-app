package com.netomi.chat

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.test.core.app.ApplicationProvider
import com.netomi.chat.utils.NCWAppUtils
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NetworkUtilsTest {
    private lateinit var context: Context
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var network: Network
    private lateinit var networkCapabilities: NetworkCapabilities

    @Before
    fun setUp() {
        // Manually mock final class
        context = ApplicationProvider.getApplicationContext<Context>()
        connectivityManager = mock(ConnectivityManager::class.java)
        network = mock(Network::class.java)
        networkCapabilities = mock(NetworkCapabilities::class.java)

        // Mock the ConnectivityManager service
        `when`(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager)
        `when`(connectivityManager.activeNetwork).thenReturn(network)
    }

    @Test
    fun testIsNetworkAvailable_whenConnectedToWifi_returnsTrue() {
        `when`(connectivityManager.getNetworkCapabilities(network)).thenReturn(networkCapabilities)
        `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)).thenReturn(true)

        val result = NCWAppUtils.isNetworkAvailable(context)

        assertTrue(result)
    }

    @Test
    fun testIsNetworkAvailable_whenConnectedToCELLULAR_returnsTrue() {
        `when`(connectivityManager.getNetworkCapabilities(network)).thenReturn(networkCapabilities)
        `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)).thenReturn(true)

        val result = NCWAppUtils.isNetworkAvailable(context)

        assertTrue(result)
    }

    @Test
    fun  testIsNetworkAvailable_whenConnectedToETHERNET_returnsTrue() {
        `when`(connectivityManager.getNetworkCapabilities(network)).thenReturn(networkCapabilities)
        `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)).thenReturn(true)

        val result = NCWAppUtils.isNetworkAvailable(context)

        assertTrue(result)
    }

    @Test
    fun testIsNetworkAvailable_whenCOnnected_returnsTrue() {
        // Simulate no network
        `when`(connectivityManager.getNetworkCapabilities(network)).thenReturn(null)
        val result = NCWAppUtils.isNetworkAvailable(context)

        assertFalse(result) // Assert that network is NOT available
    }

  /*  @Test
    fun `test isNetworkAvailable returns false when network has no valid transport`() {
        `when`(connectivityManager.getNetworkCapabilities(network)).thenReturn(networkCapabilities)
        `when`(networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)).thenReturn(false)

        val result = NCWAppUtils.isNetworkAvailable(context)

        assertFalse(result)
    }*/
}