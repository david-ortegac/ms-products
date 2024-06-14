package com.unir.productos.controller;

import com.unir.productos.model.pojo.Product;
import com.unir.productos.model.pojo.ProductDTO;
import com.unir.productos.model.request.CreateProductRequest;
import com.unir.productos.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts(
            @Valid ProductDTO searchParams
    ) {
        List<Product> products = productsService.getProducts(
                searchParams.getName(),
                searchParams.getCategory(),
                searchParams.getManufacturer(),
                searchParams.getGuarantyMonths(),
                searchParams.getPrice()
        );

        if (products != null) {
            return ResponseEntity.ok(products);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable String id) {

        Product product = productsService.getProductById(id);

        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {

        Boolean removed = productsService.deleteProduct(id);

        if (Boolean.TRUE.equals(removed)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Product> addProduct(@RequestBody CreateProductRequest request) {

        Product createdProduct = productsService.addProduct(request);

        if (createdProduct != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> patchProduct(@PathVariable String id, @RequestBody String patchBody) {

        Product patched = productsService.updateProduct(id, patchBody);
        if (patched != null) {
            return ResponseEntity.ok(patched);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody ProductDTO body) {
        Product updated = productsService.updateProduct(id, body);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
