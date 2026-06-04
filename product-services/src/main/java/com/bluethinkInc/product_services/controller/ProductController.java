package com.bluethinkInc.product_services.controller;

import com.bluethinkInc.product_services.model.Product;
import com.bluethinkInc.product_services.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @PostMapping("/add-product")
    public ResponseEntity<?> addProductController(@RequestBody Product product){
        try{
            productService.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(product);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Product not added" + e.getMessage());
        }
    }

    @GetMapping("/viewall-produc")
    public ResponseEntity<List<Product>> viewAllProductsController(){
        try{
            List<Product> products = productService.viewAllProductService();
            return ResponseEntity.ok(products);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductByIdController(@PathVariable Long id){
        try{
            Product product = productService.getProductByIdService(id);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(product);
        }catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found with this id: "+ e.getMessage());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong" + e.getMessage());
        }
    }

    @PutMapping("/update-product")
    public ResponseEntity<?> updateProductController(@RequestBody Long productId){
        try{
            productService.updateProductByIdService(productId);
            return ResponseEntity.ok("Product updated successfully");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Product not found with this id: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete-product/{id}")
    public ResponseEntity<?> deleteProductController(@PathVariable Long id){
        try{
            boolean isDeleted = productService.deleteProductService(id);
            if(isDeleted){
                return ResponseEntity.ok("Product deleted successfully");
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Not deleted successfully");
            }
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong" + e.getMessage());
        }
    }
}
