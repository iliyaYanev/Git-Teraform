package common

import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

@Slf4j
class ApiConsumer {
    static executeRequest(String path, String port, Method method, _body = null) {
        def baseUrl = "http://localhost:${port}"
        def dataToReturn = [:]
        def http = new HTTPBuilder(baseUrl)

        http.setHeaders([Accept: '*/*'])

        try {
            http.request(method, ContentType.JSON) { req ->
                uri.path = path

                if (_body != null) {
                    body = _body
                }

                response.success = { resp, reader ->
                    dataToReturn = getRequestResponse(resp, reader)
                    log.info("Request successful! ${uri} returned: ${resp.statusLine.statusCode} || ${reader}")
                }
                response.failure = { resp, reader ->
                    dataToReturn = getRequestResponse(resp, reader)
                    log.info("Request failed! ${uri} returned: ${resp} || ${reader}")
                }
            }
        } catch (Exception ex) {
            log.info("executeRequest ${http.uri} returned: ${ex.getMessage()}")
        } finally {
            http.shutdown()
            return dataToReturn
        }
    }

    static getRequestResponse(resp, reader) {
        return [statusCode: resp.statusLine.statusCode, body: reader]
    }
}
