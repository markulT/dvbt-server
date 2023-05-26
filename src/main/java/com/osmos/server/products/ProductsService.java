package com.osmos.server.products;

import com.osmos.server.minio.FileManager;
import com.osmos.server.products.dto.CategoryDto;
import com.osmos.server.products.dto.CreateProductDto;
import com.osmos.server.products.dto.CreateShortProductDto;
import com.osmos.server.products.dto.ProductDTO;
import com.osmos.server.products.entities.Category;
import com.osmos.server.products.entities.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProductsService {

    private final ProductsRepo productsRepo;
    private final CategoryRepo categoryRepo;
    private final FileManager fileManager;

    public List<ProductDTO> getAll(int pageNumber) {
        return productsRepo.findAll(PageRequest.of(pageNumber, 20)).getContent().stream().map(
                ProductDTO::clone
        ).toList();
    }

    public List<ProductDTO> getAll() {
        return productsRepo.findAll().stream().map(
                ProductDTO::clone
        ).toList();
    }


    public ProductDTO addProduct(CreateProductDto productDTO) {
        Product product = Product.builder()
                .name(productDTO.getName())
                .imgName(productDTO.getImgName())
                .title(productDTO.getTitle())
                .category(productDTO.getCategory())
                .price(productDTO.getPrice())
                .amplification(productDTO.getAmplification())
                .chanel(productDTO.getChanel())
                .packagement(productDTO.getPackagement())
                .currentConsumption(productDTO.getCurrentConsumption())
                .length(productDTO.getLength())
                .description(productDTO.getDescription())
                .build();
        productsRepo.save(product);
        return ProductDTO.clone(product);
    }

    public boolean deleteProduct(UUID id) {
        productsRepo.deleteById(id);
        return true;
    }

    public ProductDTO update(UUID id, String fieldToUpdate, Object fieldValue) {

        Product product = productsRepo.getProductById(id);
        Field field = ReflectionUtils.findField(Product.class, fieldToUpdate);
        if (field == null) {
            return null;
        }
        field.setAccessible(true);
        ReflectionUtils.setField(field, product, fieldValue);
        ProductDTO productDTO = ProductDTO.clone(product);
        productsRepo.save(product);
        return productDTO;
    }

    public CategoryDto createCategory(String name) {
        Category category = Category.builder().name(name).build();
        categoryRepo.save(category);
        return CategoryDto.builder().name(category.getName()).id(category.getId().toString()).build();
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepo.findAll().stream().map(
                        category -> CategoryDto.builder()
                                .name(category.getName()).
                                id(category.getId().toString()).build()
                )
                .toList();
    }

    public boolean deleteCategory(UUID uuid) {
        categoryRepo.deleteById(uuid);
        return true;
    }

    public boolean deleteCategory(String id) {
        return deleteCategory(UUID.fromString(id));
    }
    public ProductDTO getProduct(String id) {
        Product product = productsRepo.findById(UUID.fromString(id)).orElseThrow();
        return ProductDTO.clone(product);
    }

    public CreateShortProductDto createShortProduct(CreateShortProductDto createShortProductDto) {
        Product product = Product.builder()
                .price(createShortProductDto.getPrice())
                .title(createShortProductDto.getTitle())
                .description(createShortProductDto.getDesc())
                .imgName(createShortProductDto.getImgName())
                .name(createShortProductDto.getName())
                .build();
        productsRepo.save(product);
        return CreateShortProductDto.copy(product);
    }

    public String updateImage(MultipartFile file, String productId) {
        Product product = productsRepo.findById(UUID.fromString(productId)).orElseThrow();
        fileManager.deleteFile("image-bucket", productId);
        product.setImgName(productId);
        productsRepo.save(product);
        return fileManager.uploadFile(file, "image-bucket", product.getId().toString());
    }

    public InputStream getImage(String imageName) {
        return fileManager.downloadFile("image-bucket", imageName);
    }

    public String getImgLink(String imageName) {
        return fileManager.getFileLink("image-bucket", imageName);
    }

}
