package com.osmos.server.orders.dto;

import com.osmos.server.products.dto.ProductDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FullOrderDto {
    protected String location;
    protected String orderedBy;
    protected String orderedFullName;
    protected double finalPrice;
    protected List<ProductDTO> productList;

}
