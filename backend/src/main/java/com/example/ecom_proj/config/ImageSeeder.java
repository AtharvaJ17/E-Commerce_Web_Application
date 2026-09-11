package com.example.ecom_proj.config;

import com.example.ecom_proj.model.Product;
import com.example.ecom_proj.repo.ProductRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class ImageSeeder implements CommandLineRunner {

    private final ProductRepo repo;

    public ImageSeeder(ProductRepo repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {
        repo.findAll().forEach(product -> {
            if (product.getImageData() != null && product.getImageData().length > 0) {
                return; // already has an image
            }

            String fileName = product.getId() + ".jpg";
            ClassPathResource resource = new ClassPathResource("product-images/" + fileName);

            if (!resource.exists()) {
                System.err.println("No seed image for product " + product.getId());
                return;
            }

            try (InputStream is = resource.getInputStream()) {
                product.setImageData(is.readAllBytes());
                product.setImageName(fileName);
                product.setImageType("image/jpeg");
                repo.save(product);
                System.out.println("Seeded image for product " + product.getId());
            } catch (IOException e) {
                System.err.println("Failed seeding product " + product.getId() + ": " + e.getMessage());
            }
        });
    }
}