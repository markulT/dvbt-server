package com.osmos.server.products;


import com.osmos.server.products.dto.*;
import com.osmos.server.responseDto.CreateEntity;
import com.osmos.server.responseDto.GetAll;
import com.osmos.server.responseDto.GetPage;
import com.osmos.server.responseDto.GetSingle;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
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

    @GetMapping("/page")
    public ResponseEntity<GetPage<ProductDTO>> getPage(@RequestParam int pageNumber, @RequestParam int pageSize) {

        return ResponseEntity.ok(
                GetPage.<ProductDTO>builder()
                        .page(productsService.getAll(pageNumber, pageSize))
                        .length(productsService.getProductAmount())
                        .build()
        );
    }

    @GetMapping("/products/{categoryId}")
    public ResponseEntity<GetPage<ProductDTO>> getAllProductsByCategory(@PathVariable("categoryId") String categoryId, @RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(GetPage.<ProductDTO>builder()
                        .page(productsService.getAllByCategory(categoryId, pageNumber, pageSize))
                        .length(productsService.getProductAmountInCategory(categoryId))
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSingle<ProductDTO>> getProduct(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(GetSingle.<ProductDTO>builder().item(productsService.getProduct(id)).build());
    }


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CreateEntity<ProductDTO>> add(@RequestBody() CreateProductDto createProductDto) {
        System.out.println(createProductDto);
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

    @PutMapping("/update/category")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<GetSingle<ProductDTO>> updateCategory(@RequestBody() AssignCategoryDto assignCategoryDto) {
        ProductDTO updatedProduct = productsService.updateProductCategory(assignCategoryDto.getProductId(), assignCategoryDto.categoryId);
        return ResponseEntity.ok(GetSingle.<ProductDTO>builder().item(updatedProduct).build());
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

    @PutMapping("/image/update")
    public ResponseEntity<?> updateImage(@RequestParam("file") MultipartFile file, @RequestParam("productId") String productId) {
        System.out.println(productId);
        return ResponseEntity.ok(productsService.updateImage(file, productId));
    }

    @GetMapping("/imageUrl/{name}")
    public ResponseEntity<?> getImage(@PathVariable("name") String imgName) {
        System.out.println("getting image");
        InputStream inputStream = productsService.getImage(imgName);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment", "filename.ext");
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
        return new ResponseEntity<>(inputStreamResource, httpHeaders, HttpStatus.OK );
    }

}
