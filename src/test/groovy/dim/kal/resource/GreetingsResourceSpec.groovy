package dim.kal

import spock.lang.Specification
import io.restassured.RestAssured
class GreetingsResourceSpec extends Specification{

    def "test hello world endpoint"() {
        when:
        def response = RestAssured.get("/")

        then:
        response.statusCode() == 200
        response.body().asString().contains("Hello World")
    }
}
