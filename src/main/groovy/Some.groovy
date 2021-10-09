import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.temporal.TemporalAccessor

currentTimeMs = System.currentTimeMillis()

date = new Date(currentTimeMs)
formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")

println formatter.format(date)