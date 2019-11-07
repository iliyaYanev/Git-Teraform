import ch.qos.logback.classic.encoder.PatternLayoutEncoder

import static ch.qos.logback.classic.Level.*

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} %-5level %logger - %msg%n"
    }
}
logger("org.apache.http", INFO)
logger("org.apache.http.wire", INFO)
root(INFO, ["STDOUT"])