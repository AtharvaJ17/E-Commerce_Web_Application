package com.example.ecom_proj.controller;

import com.example.ecom_proj.model.Product;
import com.example.ecom_proj.service.ProductService;
import jakarta.persistence.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("")
    public String greet() {
        return "Hello";
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }


    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Product product = service.getProductById(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);  // Changed to OK
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
        try {
            Product product1 = service.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable Integer productId) {
        Product product = service.getProductById(productId);

        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (product.getImageData() == null || product.getImageData().length == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        String imageType = product.getImageType();
        if (imageType == null || imageType.isEmpty()) {
            imageType = "image/jpeg";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(imageType))
                .body(product.getImageData());
    }

    @PutMapping("/product/{id}")  // Changed to {id} for consistency
    public ResponseEntity<String> updateProduct(@PathVariable Integer id,
                                                @RequestPart Product product,
                                                @RequestPart MultipartFile imageFile) {
        try {
            Product product1 = service.updateProduct(id, product, imageFile);
            if (product1 != null) {
                return new ResponseEntity<>("Updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/product/{id}")  // Changed to {id} for consistency
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        Product product = service.getProductById(id);
        if (product != null) {
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/product/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> products = service.searchProducts(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}