package client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Product;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.jersey.client.ClientConfig;

public class ProductClient {
    
    private static final String BASE_URI_PRODUCTS = "http://localhost:8080/CRUD_RestWS/crud/products";
    
    private static final ClientConfig clientConfig = new ClientConfig();
    private static final Client client = ClientBuilder.newClient(clientConfig);
    
    private static ProductClient instance;
    
    private ProductClient() {
        super();
    }
    
    public static ProductClient getInstance() {
        if (instance == null) {
            return new ProductClient();
        } else {
            return instance;
        }
    }
    
    private ArrayList<Product> productList;
    
    private <T> T convertFromJson(String json, TypeReference<T> type) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, type);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(ProductClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    /*
        Request from API
    */
    
    public ArrayList<Product> getProductList() {
        WebTarget target = client.target(BASE_URI_PRODUCTS)
                .path("list");
        
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        
        Response response = invocationBuilder.get();
        String json;
        if (response.getStatus() == 200) {
             json = response.readEntity(String.class);
             productList = this.convertFromJson(json, new TypeReference<ArrayList<Product>>() {});
             
             return productList;
        } else {
            return new ArrayList<>();
        }
    }
    
    public Product getProduct(int id) {
        WebTarget target = client.target(BASE_URI_PRODUCTS)
                .path("get/" + id);
                
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        
        Response response = invocationBuilder.get();
        String json;
        if (response.getStatus() == 200) {
             json = response.readEntity(String.class);
             Product product = this.convertFromJson(json, new TypeReference<Product>() {});
             
             return product;
        } else {
            return null;
        }    
    }
    
}
