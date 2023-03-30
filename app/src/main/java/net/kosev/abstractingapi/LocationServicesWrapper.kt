package net.kosev.abstractingapi

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.Task
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LocationServicesWrapper @Inject constructor(
    private val client: FusedLocationProviderClient
) {

    @SuppressLint("MissingPermission")
    suspend fun getLastLocation(): Location =
        client.lastLocation.await()
}

suspend fun <TResult> Task<TResult>.await(): TResult =
    suspendCoroutine { continuation ->
        addOnSuccessListener { continuation.resume(it) }
        addOnFailureListener { continuation.resumeWithException(it) }
    }
