package de.hilling.jee.service;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/marshall")
@Produces(MediaType.APPLICATION_JSON)
public class MarshallingManually {

    @GET
    @Path("{orderId:\\d+}/items/{itemId:[a-z]+\\d{2,7}}")
    public Response orders(@PathParam("orderId") String orderId, @PathParam("itemId") String itemId) {
        final JsonObject content = Json.createObjectBuilder()
                                       .add("orderId", orderId)
                                       .add("itemId", itemId)
                                       .build();
        return Response.ok()
                       .header("Content-Type", "application/json")
                       .entity(content)
                       .build();
    }
}
