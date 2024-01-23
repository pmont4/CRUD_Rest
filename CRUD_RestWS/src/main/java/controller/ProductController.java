package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import entity.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.NonNull;

public class ProductController {
    
    private static ProductController instance;
    
    protected static final List<Product> productList = new ArrayList<>();
    
    private static List<Product> getProductList() {
        return productList;
    }
    
    private ObjectMapper getObjectMapper() {
        return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }
    
    static {
        productList.add(new Product("Coca-Cola", 1, 5.99f));
        productList.add(new Product("Lays", 2, 2.99f));
        productList.add(new Product("Liptone", 3, 4.99f));
    }
    
    private ProductController() {
        super();
    }
    
    public static ProductController getInstance() {
        if (instance == null) {
            instance = new ProductController();
        }
        return instance;
    }
    
    public ArrayList<Product> getAll() {
        return new ArrayList<>(productList);
    }
    
    public int add(@NonNull Product product) {
        if (getProductList().stream().anyMatch(p -> !Objects.equals(p.getId(), product.getId()))) {
            int newId = getProductList().get(getProductList().size() - 1).getId() + 1;
            
            product.setId(newId);
            productList.add(product);
            
            return newId;
        }
        return 0;
    }
    
    public Optional<Product> get(int id) {
        return getProductList().parallelStream().filter(p -> p.getId() == id).findFirst();
    }
    
    public boolean update(@NonNull Product product) {
        if (this.get(product.getId()).isPresent()) {
            int index = productList.indexOf(this.get(product.getId()).get());
            productList.set(index, product);
            return true;
        } else {
            return false;
        }
    }
    
    public boolean delete(int id) {
        if (this.get(id).isPresent()) {
            int index = productList.indexOf(this.get(id).get());
            
            productList.remove(index);
            return true;
        }
        return false;
    }
    
}
