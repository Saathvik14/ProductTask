package com.producttask.product.service;

import com.producttask.product.model.Product;

import java.util.List;

public interface ProductService {
    public Product addproduct(Product product);
    public List<Product> getprod();
    public void deleteprod(int productId);
    public Product updateProduct(int productId, Product product);
}
