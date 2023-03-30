package net.kosev.abstractingapi

import android.content.Context
import android.text.format.DateUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateFormatter @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun formatDate(calendar: Calendar): String =
        DateUtils.formatDateTime(
            context,
            calendar.timeInMillis,
            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME
        )
}
