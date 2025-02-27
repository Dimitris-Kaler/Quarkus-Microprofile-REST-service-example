package dim.kal.resource;

import dim.kal.model.Message;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * REST resource for handling greeting requests.
 * Provides endpoints to send greetings via simple GET requests, path parameters,
 * query parameters, and JSON request bodies.
 */
@Path("/")
public class GreetingsResource {

    public GreetingsResource(){

    }

    /**
     * Returns a simple greeting message.
     *
     * @return a JSON response containing a greeting message.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response helloWorld() {
        // Returns a Message object which is automatically serialized to JSON.
        return Response.ok(new Message("Hello World")).build();
    }

    /**
     * Returns a greeting message using a path parameter.
     * The name parameter is extracted from the URL.
     *
     * @param name the name extracted from the URL path
     * @return a JSON response with a personalized greeting message.
     */
    @GET
    @Path("/greet/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pathParamGreeting(@PathParam("name") String name) {
        return Response.ok(new Message("Hello " + name + "!")).build();
    }

    /**
     * Returns a greeting message using query parameters.
     * Both the name and age must be provided as query parameters.
     *
     * @param name the name provided in the query parameter
     * @param age  the age provided in the query parameter
     * @return a JSON response with a personalized greeting if both parameters are provided,
     *         or a BAD_REQUEST response if either is missing.
     */
    @GET
    @Path("/greeting")
    @Produces(MediaType.APPLICATION_JSON)
    public Response greetingWithQuery(@QueryParam("name") String name, @QueryParam("age") Integer age) {
        if (name == null || age == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(new Message("Hello my name is " + name + " and i'm " + age + " years old!")).build();
    }

    /**
     * Returns a greeting message based on the JSON data provided in the request body.
     * The request must contain both 'name' and 'age' fields.
     *
     * @param input the Message object parsed from the JSON request body
     * @return a JSON response with a personalized greeting if both fields are present,
     *         or a BAD_REQUEST response if either field is missing.
     */
    @POST
    @Path("/greet")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response greetingWithReqBody(Message input) {
        if (input.getName() == null || input.getAge() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok(new Message("Hello my name is " + input.getName() + " and i'm " + input.getAge() + " old!")).build();
    }
}
