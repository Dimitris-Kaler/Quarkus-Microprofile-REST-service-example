package dim.kal.resource

import dim.kal.model.Message
import io.quarkus.test.junit.QuarkusTest
import spock.lang.Specification
import io.restassured.RestAssured


class GreetingsResourceSpec extends Specification {

GreetingsResource resource = new GreetingsResource();


    def "test hello world endpoint"() {
        when:
        def response = resource.helloWorld()

        then:
        response.getStatus() == 200
        def message = response.readEntity(Message.class)
        message.getMsg()=="Hello World"
    }
}
