package com.producttask.product.controller;


import com.producttask.product.model.Product;
import com.producttask.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/addprod")
    public Product addproduct(@RequestBody Product product) {
        return productService.addproduct(product);

    }

    @GetMapping("/listallprod")
    public List<Product> getprod() {
        return productService.getprod();
    }

    @DeleteMapping("/delete/{prodId}")
    public String deleteProduct(@PathVariable("prodId") int productId) {
        productService.deleteprod(productId);
        return "data deleted successfully";
    }

    @PutMapping("/update/{prodId}")
    public Product updateProduct(@RequestBody Product product, @PathVariable("prodId") int productId) {
        return productService.updateProduct(productId, product);
    }


}
