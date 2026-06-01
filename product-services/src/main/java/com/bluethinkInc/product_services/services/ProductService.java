package com.bluethinkInc.product_services.services;

import com.bluethinkInc.product_services.model.Product;
import com.bluethinkInc.product_services.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public void addProduct(Product product) {
        productRepo.save(product);
    }

    public Product getProductByIdService(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));
    }

    public List<Product> viewAllProductService() {
        return productRepo.findAll();
    }

    public Product updateProductByIdService(Long productId) {
        return productRepo.save(productRepo.findById(productId)
                .orElseThrow(()->
                        new RuntimeException("Product not found with this id")));
    }

    public boolean deleteProductService(Long id) {
         productRepo.deleteById(id);
         return true;
    }
}
