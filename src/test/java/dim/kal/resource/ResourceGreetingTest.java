package dim.kal.resource;

import dim.kal.model.Message;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests for the Greeting resource endpoints.
 * <p>
 * This test class uses REST Assured with the embedded Quarkus server (via {@code @QuarkusTest})
 * to verify that the Greeting resource responds correctly for various scenarios.
 * </p>
 */
@QuarkusTest
public class ResourceGreetingTest {

    /**
     * Tests the root endpoint ("/") to verify that it returns the message "Hello World".
     */
    @Test
    public void testHelloWorldEndpoint() {
        Response response = RestAssured.get("/");
        assertEquals(200, response.statusCode(), "Expected status code 200");
        Message message = response.as(Message.class);
        assertEquals("Hello World", message.getMsg(), "Message should equal 'Hello World'");
    }

    /**
     * Tests the /greet/{name} endpoint for multiple names.
     * <p>
     * The test iterates over an array of names and verifies that each call to /greet/{name} returns
     * a message in the format "Hello {name}!" with a status code of 200.
     * </p>
     */
    @Test
    public void testPathParamGreetingEndpoint() {
        String[] names = {"Tsekouras", "Xantakias", "Petaloudas", "Sougias", "Fasarias"};
        for (String name : names) {
            Response response = RestAssured.get("/greet/{name}", name);
            assertEquals(200, response.statusCode(), "Expected status code 200 for name: " + name);
            Message message = response.as(Message.class);
            String expectedGreeting = "Hello " + name + "!";
            assertEquals(expectedGreeting, message.getMsg(), "Message should equal '" + expectedGreeting + "'");
        }
    }

    /**
     * Tests that accessing the /greet/ endpoint without a path parameter returns a 405 Method Not Allowed status.
     */
    @Test
    public void testPathParamGreetingEndpointWithoutParameter() {
        Response response = RestAssured.get("/greet/");
        assertEquals(405, response.statusCode(), "Expected status code 405 when no path parameter is provided");
    }

    /**
     * Tests the /greeting endpoint with valid query parameters.
     * <p>
     * For each set of query parameters provided via a two-dimensional array, the test verifies that the
     * endpoint returns the expected personalized greeting message and a status code of 200.
     * </p>
     */
    @Test
    public void testQueryParamsGreetingEndpoint() {
        Object[][] testData = {{"John Xantakias", 42, "Hello my name is John Xantakias and i'm 42 years old!"}, {"Nick Petaloudas", 53, "Hello my name is Nick Petaloudas and i'm 53 years old!"}};
        for (Object[] row : testData) {
            String name = (String) row[0];
            Integer age = (Integer) row[1];
            String expectedMessage = (String) row[2];
            Response response = RestAssured.given().queryParam("name", name).queryParam("age", age).get("/greeting");
            assertEquals(200, response.statusCode(), "Expected status code 200 for name: " + name + " and age: " + age);
            Message message = response.as(Message.class);
            assertEquals(expectedMessage, message.getMsg(), "Expected message: '" + expectedMessage + "' for name: " + name + " and age: " + age);
        }
    }

    /**
     * Tests that the /greeting endpoint returns a 400 Bad Request when required query parameters are missing.
     * <p>
     * This test uses a two-dimensional array with missing parameters, and builds the query URL dynamically,
     * omitting parameters that are null.
     * </p>
     */
    @Test
    public void testQueryParamsGreetingFails() {
        Object[][] testData = {{"Bob Knife", null}, {"Harry The Axe", null}, {null, 33}, {null, null}};
        for (Object[] row : testData) {
            String name = (String) row[0];
            Integer age = (Integer) row[1];
            String url = buildQueryURL("/greeting", name, age);
            Response response = RestAssured.get(url);
            assertEquals(400, response.statusCode(), "Expected status code 400 for name: " + name + " and age: " + age);
        }
    }

    /**
     * Helper method that builds a URL with query parameters, including only those that are non-null.
     *
     * @param baseURI The base URI (e.g., "/greeting").
     * @param name    The value for the 'name' parameter, or null if not provided.
     * @param age     The value for the 'age' parameter, or null if not provided.
     * @return A URL string with the non-null query parameters appended.
     */
    private String buildQueryURL(String baseURI, String name, Integer age) {
        StringBuilder builder = new StringBuilder(baseURI);
        boolean firstParam = true;
        if (name != null) {
            builder.append(firstParam ? "?" : "&").append("name=").append(name);
            firstParam = false;
        }
        if (age != null) {
            builder.append(firstParam ? "?" : "&").append("age=").append(age);
        }
        return builder.toString();
    }

    /**
     * Tests the /greet POST endpoint with a valid JSON request body.
     * <p>
     * Verifies that the endpoint returns the expected greeting message when a valid Message object is provided.
     * </p>
     */
    @Test
    public void testGreetingWithRequestBodyEndpoint() {
        Message input = new Message();
        input.setName("Bob Trench");
        input.setAge(25);
        Response response = RestAssured.given().contentType("application/json").body(input).post("/greet");
        assertEquals(200, response.statusCode(), "Expected status code 200");
        Message message = response.as(Message.class);
        String expected = "Hello my name is " + input.getName() + " and i'm " + input.getAge() + " old!";
        assertEquals(expected, message.getMsg(), "Message should equal '" + expected + "'");
    }

    /**
     * Tests that the /greet POST endpoint returns a 415 Unsupported Media Type when no request body is provided.
     */
    @Test
    public void testGreetingWithMissingRequestBody() {
        Response response = RestAssured.post("/greet");
        assertEquals(415, response.statusCode(), "Expected status code 415");
    }

    /**
     * Tests that the /greet POST endpoint returns a 400 Bad Request when provided with an invalid request body.
     * <p>
     * This test sends various invalid Message objects (with missing required fields) and verifies that a 400 status code is returned.
     * </p>
     */
    @Test
    public void testGreetingWithInvalidRequestBody() {
        Message msg1 = new Message();
        msg1.setName("Harry");
        Message msg2 = new Message();
        msg2.setAge(19);
        Message msg3 = new Message();
        Message[] invalidMessages = {msg1, msg2, msg3};
        for (Message msg : invalidMessages) {
            Response response = RestAssured.given().contentType("application/json").body(msg).post("/greet");
            assertEquals(400, response.statusCode(), "Expected 400 status code for invalid request body");
        }
    }
}
