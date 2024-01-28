package resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import controller.ProductController;
import entity.Product;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import util.JsonUtil;

@Path("products")
public class ProductResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    private final ProductController P_CONTROLLER = ProductController.getInstance();

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProducts() {
        return Response.ok(
                this.getP_CONTROLLER().getAll(), MediaType.APPLICATION_JSON
        ).build();
    }

    @GET
    @Path("get/{id}")
    @Produces({"application/json", "text/plain"})
    public Response getProduct(@PathParam("id") int id) {
        Response toReturn;
        
        if (this.getP_CONTROLLER().get(id).isPresent()) {
            Product product = this.getP_CONTROLLER().get(id).get();
            toReturn = Response.ok(
                    product, MediaType.APPLICATION_JSON
            ).build();
        } else {
            toReturn = Response.status(Status.NOT_FOUND).entity("The ID of the product wasn't found in the product list.").build();
        }
        
        return toReturn;
    }

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({"application/json", "text/plain"})
    public Response addProduct(String json) {
        Response toReturn = null;
        
        try {
            Product product = JsonUtil.getInstance().getFromJson(json, new TypeReference<Product>(){
            });
            
            if (this.getP_CONTROLLER().add(product)) {
                toReturn = Response.ok(
                        this.getP_CONTROLLER().getAll(), MediaType.APPLICATION_JSON
                ).build();
            } else {
                toReturn = Response.status(Status.NOT_IMPLEMENTED).entity("The product couldn't be added.").build();
            }
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ProductResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return toReturn;
    }

    @DELETE
    @Path("remove/{id}")
    @Produces({"application/json", "text/plain"})
    public Response removeProduct(@PathParam("id") int id) {
        Response toReturn = null;
        
        if (this.getP_CONTROLLER().get(id).isPresent()) {
            if (this.getP_CONTROLLER().delete(id)) {
                toReturn = Response.ok(
                        this.getP_CONTROLLER().getAll(), MediaType.APPLICATION_JSON
                ).build();
            }
        } else {
            toReturn = Response.status(Status.NOT_FOUND).entity("The ID of the product wasn't found in the product list.").build();
        }

        return toReturn;
    }

    @PUT
    @Path("modify")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifyProduct(String json) {
        Response toReturn = null;
        
        try {
            Product product = JsonUtil.getInstance().getFromJson(json, new TypeReference<Product>(){
            });
            
            if (this.getP_CONTROLLER().update(product)) {
                toReturn = Response.ok(
                        this.getP_CONTROLLER().getAll(), MediaType.APPLICATION_JSON
                ).build();
            } else {
                toReturn = Response.status(Status.NOT_FOUND).entity("The id of the product wasn't found in the product list.").build();
            }
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ProductResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return toReturn;
    }

}
