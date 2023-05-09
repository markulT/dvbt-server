package com.osmos.server.products;


import com.osmos.server.products.dto.*;
import com.osmos.server.responseDto.CreateEntity;
import com.osmos.server.responseDto.GetAll;
import com.osmos.server.responseDto.GetSingle;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;


//    TODO: Simple CRUD API

    @GetMapping("/all")
    public ResponseEntity<GetAll<ProductDTO>> getAll() {
        return ResponseEntity.ok(
                GetAll.<ProductDTO>builder().
                        list(productsService.getAll())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSingle<ProductDTO>> getProduct(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(GetSingle.<ProductDTO>builder().item(productsService.getProduct(id)).build());
    }


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CreateEntity<ProductDTO>> add(@RequestBody() CreateProductDto createProductDto) {
        return ResponseEntity.ok(
                CreateEntity.<ProductDTO>builder()
                        .entity(productsService.addProduct(createProductDto))
                        .build()
        );
    }

    @PostMapping("/addShort")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CreateEntity<CreateShortProductDto>> addShortProduct(@RequestBody() CreateShortProductDto createShortProductDto) {
        return ResponseEntity.ok(
                CreateEntity.<CreateShortProductDto>builder()
                        .entity(productsService.createShortProduct(createShortProductDto))
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(productsService.deleteProduct(id));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> update(@RequestBody() UpdateProductDTO updateProductDTO) {
        ProductDTO updatedProduct = productsService.update(UUID.fromString(updateProductDTO.getId()), updateProductDTO.getFieldToChange(), updateProductDTO.getFieldValue());
        return ResponseEntity.ok(updatedProduct);
    }

    @PostMapping("/create/category")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CreateEntity<CategoryDto>> createCategory(@RequestBody() CreateCategoryDto createCategoryDto) {
        return ResponseEntity.ok(CreateEntity.
                <CategoryDto>builder()
                .entity(
                        productsService.createCategory(createCategoryDto.getName())
                )
                .build());
    }

    @DeleteMapping("/delete/category/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") String id) {
        return ResponseEntity.ok(productsService.deleteCategory(UUID.fromString(id)));
    }

    @GetMapping("/getAll/categories")
    public ResponseEntity<GetAll<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(GetAll.<CategoryDto>builder()
                .list(productsService.getAllCategories())
                .build());
    }

}
