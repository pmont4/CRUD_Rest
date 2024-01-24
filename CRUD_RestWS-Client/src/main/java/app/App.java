package app;

import client.ProductClient;

public class App {
    
    public static void main(String[] args) {
        ProductClient.getInstance().getProductList().forEach(p -> {
            System.out.println("Product name: " + p.getName() + " | price: " + p.getPrice());
        });
        
        System.out.println("\n" + ProductClient.getInstance().getProduct(1).toString());
        System.out.println("\n" + ProductClient.getInstance().addProduct("AirPods", 299.99f));
        
        System.out.println("\n");
        ProductClient.getInstance().getProductList().forEach(p -> {
            System.out.println("Product name: " + p.getName() + " | price: " + p.getPrice());
        });
        
        System.out.println("\n");
        System.out.println(ProductClient.getInstance().updateProduct(1, "Pepsi", 4.99f));
        ProductClient.getInstance().getProductList().forEach(p -> {
            System.out.println("Product name: " + p.getName() + " | price: " + p.getPrice());
        });
        
        System.out.println("\n");
        System.out.println(ProductClient.getInstance().deleteProduct(2));
    }
    
}
