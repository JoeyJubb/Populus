package uk.co.bubblebearapps.data.users.stackexchange.json

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import java.lang.reflect.Type
import javax.inject.Inject

/**
 * Deserializes unix time stamps into Instant instances
 */
class ZonedDateTimeDeserializer @Inject constructor() : JsonDeserializer<ZonedDateTime> {

    override fun deserialize(
            json: JsonElement,
            typeOfT: Type,
            context: JsonDeserializationContext
    ): ZonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochSecond(json.asLong), ZoneId.of("UTC"))
            .withZoneSameInstant(
                    ZoneId.systemDefault()
            )
}