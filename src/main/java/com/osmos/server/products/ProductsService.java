package com.osmos.server.products;

import com.osmos.server.minio.FileManager;
import com.osmos.server.orders.OrderItemRepo;
import com.osmos.server.orders.entities.OrderItem;
import com.osmos.server.products.dto.CategoryDto;
import com.osmos.server.products.dto.CreateProductDto;
import com.osmos.server.products.dto.CreateShortProductDto;
import com.osmos.server.products.dto.ProductDTO;
import com.osmos.server.products.entities.Category;
import com.osmos.server.products.entities.Product;
import com.osmos.server.products.filters.SearchParams;
import jakarta.transaction.Transactional;
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
    private final OrderItemRepo orderItemRepo;

    private final SearchHandler searchHandler;

    @Transactional
    public List<ProductDTO> getAll(int pageNumber, int pageSize) {
        if (pageSize > 50) {
            pageSize = 50;
        }
        return productsRepo.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent().stream().map(
                ProductDTO::clone
        ).toList();
    }

    public long getProductAmount() {
        return productsRepo.count();
    }

    @Transactional
    public List<ProductDTO> getAll() {
        return productsRepo.findAll().stream().map(
                ProductDTO::clone
        ).toList();
    }

    public ProductDTO updateProductCategory(String productId, String categoryId) {
        Category category = categoryRepo.findById(UUID.fromString(categoryId)).orElseThrow();
        Product product = productsRepo.findById(UUID.fromString(productId)).orElseThrow();
        product.setCategory(category);
        productsRepo.save(product);
        return ProductDTO.clone(product);
    }

    public List<ProductDTO> getAllByCategory(String categoryId) {
        Category category = categoryRepo.findById(UUID.fromString(categoryId)).orElseThrow();
        return productsRepo.findAllByCategory(category).stream().map(ProductDTO::clone).toList();
    }

    @Transactional
    public List<ProductDTO> getAllByCategory(String categoryId, int pageNumber, int pageSize) {
        Category category = categoryRepo.findById(UUID.fromString(categoryId)).orElseThrow();
        System.out.println(category.getName());
        if (pageSize > 50) {
            pageSize = 50;
        }
        List<ProductDTO> productDTOList = productsRepo.findAllByCategory(PageRequest.of(pageNumber - 1, pageSize ),category).stream().map(ProductDTO::clone).toList();
        System.out.println(productDTOList);
        return productDTOList;
    }

    public long getProductAmountInCategory(String categoryId) {
        Category category = categoryRepo.findById(UUID.fromString(categoryId)).orElseThrow();
        return productsRepo.countAllByCategory(category);
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
        Product product = productsRepo.findById(id).orElseThrow();
        fileManager.deleteFile("image-bucket", product.getId().toString());
        productsRepo.delete(product);
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

    @Transactional
    public CategoryDto addComplementaryToCategory(String categoryId, String productId) {
        Category category = categoryRepo.findById(UUID.fromString(categoryId)).orElseThrow();
        Product product = productsRepo.findById(UUID.fromString(productId)).orElseThrow();
        List<Product> newList = category.getAdditionals();
        newList.add(product);
        category.setAdditionals(newList);
        product.setComplements(category);
        categoryRepo.save(category);
        System.out.println(category);
        return CategoryDto.builder()
                .name(category.getName())
                .id(category.getId().toString())
                .additionals(category.getAdditionals().stream().map(ProductDTO::clone).toList())
                .build();
    }

    @Transactional
    public CategoryDto removeComplementaryFromCategory(String categoryId, String productId) {
        Category category = categoryRepo.findById(UUID.fromString(categoryId)).orElseThrow();
        Product product = productsRepo.findById(UUID.fromString(productId)).orElseThrow();
        if(category.getAdditionals().contains(product)) {
            System.out.println("removing product");
            var newList = category.getAdditionals();
            newList.remove(product);
            category.setAdditionals(newList);
            product.setComplements(null);
        }
        return CategoryDto.builder()
                .name(category.getName())
                .id(category.getId().toString())
                .additionals(category.getAdditionals().stream().map(ProductDTO::clone).toList())
                .build();
    }

    public CategoryDto createCategory(String name) {
        Category category = Category.builder().name(name).build();
        categoryRepo.save(category);
        return CategoryDto.builder().name(category.getName()).id(category.getId().toString()).build();
    }

    @Transactional
    public CategoryDto getCategory(String id) {
        Category category = categoryRepo.findById(UUID.fromString(id)).orElseThrow();
        System.out.println(category.getAdditionals());
        return CategoryDto.builder()
                .additionals(category.getAdditionals().stream().map(ProductDTO::clone).toList())
                .name(category.getName())
                .id(category.getId().toString())
                .build();
    }

    public ProductDTO getProductByItemId(String id) {
        OrderItem orderItem = orderItemRepo.findById(UUID.fromString(id)).orElseThrow();
        return ProductDTO.clone(orderItem.getProduct());
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
    @Transactional
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

    public void test() {
    }

    public List<ProductDTO> search(double distance) {
        return null;
    }

    public List<ProductDTO> search(double distance, int obstacle) {
        return null;
    }

    public List<ProductDTO> search(double distance, int obstacle, int geo) {
        double rating = searchHandler.search(new SearchParams(distance,obstacle,geo));
        List<Product> products = productsRepo.findProductsByRangeInMeters(rating);
        return products.stream().map(ProductDTO::clone).toList();
    }

}
