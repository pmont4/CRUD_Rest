package client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import entity.Product;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.jersey.client.ClientConfig;
import util.JsonUtil;

public class ProductClient implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private static final String BASE_URI_PRODUCTS = "http://localhost:8080/CRUD_RestWS/crud/products";

    private final ClientConfig clientConfig;
    private final Client client;

    private static ProductClient instance;

    private ProductClient() {
        super();
        this.clientConfig = new ClientConfig();
        this.client = ClientBuilder.newClient(clientConfig);
    }

    public static ProductClient getInstance() {
        if (instance == null) {
            return new ProductClient();
        } else {
            return instance;
        }
    }

    private ArrayList<Product> productList;

    /*
        Request from API
     */
    
    public ArrayList<Product> getProductList() {
        WebTarget target = client.target(BASE_URI_PRODUCTS)
                .path("list");

        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);

        try (Response response = invocationBuilder.get()) {
            String json;
            if (response.getStatus() == 200) {
                json = response.readEntity(String.class);
                try {
                    productList = JsonUtil.getInstance().getFromJson(json, new TypeReference<ArrayList<Product>>(){
                    });
                } catch (JsonProcessingException ex) {
                    Logger.getLogger(ProductClient.class.getName()).log(Level.SEVERE, null, ex);
                }

                return productList;
            } else {
                return new ArrayList<>();
            }
        }
    }

    public Product getProduct(int id) {
        WebTarget target = client.target(BASE_URI_PRODUCTS)
                .path("get/" + id);

        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);

        try (Response response = invocationBuilder.get()) {
            String json;
            if (response.getStatus() == 200) {
                json = response.readEntity(String.class);
                Product product;
                try {
                    product = JsonUtil.getInstance().getFromJson(json, new TypeReference<Product>(){
                    });
                    
                    return product;
                } catch (JsonProcessingException ex) {
                    Logger.getLogger(ProductClient.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                return null;
            }
        }
        
        return null;
    }

    public String addProduct(String name, Float price) {
        String result;

        Integer id = !this.getProductList().isEmpty() ? this.getProductList().get(this.getProductList().size() - 1).getId() + 1 : 1;
        Product product = new Product(name, price);
        product.setId(id);

        WebTarget target = client.target(BASE_URI_PRODUCTS)
                .path("add");
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        try (Response response = invocationBuilder.post(Entity.json(product))) {
            if (response.getStatus() == 200) {
                result = response.readEntity(String.class);
            } else {
                result = response.getStatus() + " > " + response.getStatusInfo();
            }
        }

        return result;
    }
    
    public String updateProduct(Integer id, String name, Float price) {
        String result;
        
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        
        WebTarget target = client.target(BASE_URI_PRODUCTS)
                .path("modify");
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        try (Response response = invocationBuilder.put(Entity.json(product))) {
            if (response.getStatus() == 200) {
                result = response.readEntity(String.class);
            } else {
                result = response.getStatus() + " > " + response.getStatusInfo();
            }
        }
        
        return result;
    }
    
    public String deleteProduct(Integer id) {
        String result;
        
        WebTarget target = client.target(BASE_URI_PRODUCTS)
                .path("remove/" + id);
        Invocation.Builder invocationBuilder = target.request(MediaType.APPLICATION_JSON);
        try (Response response = invocationBuilder.delete()) {
            if (response.getStatus() == 200) {
                result = response.readEntity(String.class);
            } else {
                result = response.getStatus() + " > " + response.getStatusInfo();
            }
        }
        
        return result;
    }
    
}
