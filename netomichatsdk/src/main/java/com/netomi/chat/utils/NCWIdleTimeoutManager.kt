import android.os.SystemClock
import android.util.Log

class NCWIdleTimeoutManager(private val idleTimeoutMillis: Long, private val onTimeout: () -> Unit) {

    private var lastActiveTime: Long = SystemClock.elapsedRealtime()

    // Function to update the last active time (called on user interaction)
    fun updateLastActiveTime() {
        lastActiveTime = SystemClock.elapsedRealtime()
        Log.e("TimeOut", "lastActiveTime updated to: $lastActiveTime")
    }

    // Function to check if the session has timed out
   /* fun checkForTimeout() {
        val currentTime = SystemClock.elapsedRealtime()
        val timeDifference = currentTime - lastActiveTime

        Log.e("TimeOut", "currentTime: $currentTime, lastActiveTime: $lastActiveTime, timeDifference: $timeDifference")
        Log.e("TimeOut", "idleTimeoutMillis: $idleTimeoutMillis")

        // Check if the time difference exceeds the idle timeout threshold
        if (timeDifference > idleTimeoutMillis) {
            Log.e("TimeOut", "Session timed out! timeDifference: $timeDifference exceeds idleTimeoutMillis")
            onTimeout.invoke() // Trigger the timeout callback (e.g., show timeout popup)
        }
    }*/

    private val initializationTime: Long = SystemClock.elapsedRealtime() // Track when the class is initialized

    // Function to check if 1 minute has passed since initialization
    fun checkForTimeout() {
        val currentTime = SystemClock.elapsedRealtime()
        val timeDifference = currentTime - initializationTime

        Log.e("TimeOut", "currentTime: $currentTime, initializationTime: $initializationTime, timeDifference: $timeDifference")
        Log.e("TimeOut", "idleTimeoutMillis: $idleTimeoutMillis")

        // Check if the initialization time plus 1 minute has passed
        if (timeDifference >= idleTimeoutMillis) {
            Log.e("TimeOut", "Session timed out! 1 minute has passed since initialization")
            onTimeout.invoke() // Trigger the timeout callback (e.g., show timeout popup)
        }
    }
}
