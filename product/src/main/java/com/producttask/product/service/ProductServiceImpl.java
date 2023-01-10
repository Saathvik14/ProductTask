package com.producttask.product.service;

import com.producttask.product.model.Product;
import com.producttask.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addproduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> getprod()
    {
        return productRepository.findAll();
    }

    @Override
    public void deleteprod(int productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public Product updateProduct(int productId, Product product) {
    Optional<Product> findById = productRepository.findById(productId);
        if(findById.isPresent()){
        Product prodUpdate=findById.get();
        if(product.getProductName()!=null && !product.getProductName().isEmpty())
            prodUpdate.setProductName(product.getProductName());
        if(product.getPrice()!=0)
            prodUpdate.setPrice(product.getPrice());
        if(product.getProdQuant()!=0)
            prodUpdate.setProdQuant(product.getProdQuant());
        return productRepository.save(prodUpdate);
    }
        return null;
    }


}
