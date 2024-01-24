package app;

import client.ProductClient;

public class App {
    
    public static void main(String[] args) {
        ProductClient.getInstance().getProductList().forEach(p -> {
            System.out.println("Product name: " + p.getName() + " | price: " + p.getPrice());
        });
        
        System.out.println("\n" + ProductClient.getInstance().getProduct(1).toString());
    }
    
}
