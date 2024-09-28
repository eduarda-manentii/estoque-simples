package br.com.eduarda.simplestock.models;

import java.util.ArrayList;
import java.util.List;

public class Stock {

    private static Stock uniqueInstance;

    private static ArrayList<Product> products;

    private Stock() {
    }

    public static synchronized Stock getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new Stock();
            products = new ArrayList<Product>();
        }
        return uniqueInstance;
    }

    public void add(Product product) {
        products.add(product);
    }

    public List<Product> getList() {
        return products;
    }
}
