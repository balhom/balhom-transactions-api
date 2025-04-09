package org.balhom.transactionsapi.common.config

import io.quarkus.logging.Log
import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.event.Observes
import jakarta.inject.Singleton
import java.util.*


@Singleton
class TimeConfig {
    companion object {
        const val DATE_TIME_ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        const val DATE_ISO_FORMAT = "yyyy-MM-dd"
    }

    fun setTimezone(@Observes startupEvent: StartupEvent?) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
        Log.info("Using UTC Timezone")
    }
}