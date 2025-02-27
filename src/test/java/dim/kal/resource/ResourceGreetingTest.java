package dim.kal.resource;

import dim.kal.model.Message;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class ResourceGreetingTest {
    @Test
    public void helloWorldSpec() {
        // Send an HTTP GET request to the root endpoint "/"
        Response response = RestAssured.get("/");

        // Assert that the response status code is 200
        assertEquals(200, response.statusCode(), "Expected status code 200");

        // Deserialize the response into a Message object
        Message message = response.as(Message.class);

        assertTrue("Hello World".equals(message.getMsg()));

        // Assert that the message text contains "Hello World"
        assertEquals("Hello World", message.getMsg(), "Message should equal 'Hello World'");
    }


    @Test
    public void PathParamsGreetSpec() {
        String[] names = {"Dimitris", "Lefteris", "Panagiotis", "Polikseni", "Gogo"};

        for (String name : names) {
            Response response = RestAssured.get("/greet/{name}", name);

            assertEquals(200, response.statusCode(), "waits 200 as status code");

            Message message = response.as(Message.class);

            String msg = "Hello " + name + "!";
            System.out.println("The msg of the response is: " + msg);

            assertTrue(msg.equals(message.getMsg()));


        }

    }

    @Test
    public void PathParamsGreetSpecWithoutParam() {
        Response response = RestAssured.get("/greet/");

        assertEquals(405, response.statusCode(), "waits 405 as status code");

    }

    @Test
    public void queryParamsGreetings() {

        Object[][] values = {{"John Xantakias", 42}, {"Nick Petaloudas", 53}};
        for (Object[] row : values) {
            String name = (String) row[0];
            Integer age = (Integer) row[1];

            Response response = RestAssured.given()
                    .queryParam("name", name)
                    .queryParam("age", age)
                    .get("/greeting");

            assertTrue(response.statusCode() == 200);

            Message message = response.as(Message.class);
            System.out.println("The response Message is: " + message.getMsg());
            assertEquals("Hello my name is " + name + " and i'm " + age + " years old!", message.getMsg());
        }
    }


    @Test
    public void queryParamsGreetingsFails() {
        Object[][] values = {{"Bob Knife", null}, {"Harry The Axe", null}, {null, 33}, {null, null}};


        for (Object[] row : values) {
            String name = (String) row[0];
            Integer age = (Integer) row[1];

            Response response = RestAssured.get(buildURL("/greeting", name, age));


            assertTrue(response.statusCode() == 400);
        }


    }

    private String buildURL(String baseURI, String name, Integer age) {
        StringBuilder builder = new StringBuilder(baseURI);
        boolean firstParam = true;
        if (name != null) {
            builder.append("?name=").append(name);
            builder.append(firstParam ? "?" : "&").append("name=" + name);
            firstParam = false;
        }

        if (age != null) {
            if (age != null) {
                builder.append(firstParam ? "?" : "&").append("age=" + age);
            }


        }

        return builder.toString();

    }

    @Test
    public void greetingWithReqBody(){
        Message input = new Message();
        input.setName("Bob Trench");
        input.setAge(25);

        Response response = RestAssured.given().contentType("application/json").body(input).post("/greet");

        assertEquals(200, response.statusCode(), "Expected status code 200");

        Message message = response.as(Message.class);
        String expected = "Hello my name is " + input.getName() + " and i'm " + input.getAge() + " old!";
        assertEquals(expected, message.getMsg(), "Message should equal '" + expected + "'");


    }

    @Test
    public void greetingWithReqBodyWithNoBody(){

        Response response = RestAssured.post("/greet");

        assertEquals(415, response.statusCode(), "Expected status code 415");




    }

    @Test
    public void greetingWithNotValidBody(){
        Message msg1= new Message();
        msg1.setName("Harry");

        Message msg2 = new Message();
        msg2.setAge(19);

        Message msg3 = new Message();

        Message[] messages = {msg1,msg2,msg3};


        for(Message msg:messages){
            Response response = RestAssured.given().contentType("application/json").body(msg).post("/greet");

            assertEquals(400,response.statusCode(),"Expected 400 statusCode because of the wrong reqBody");

        }







    }


}
