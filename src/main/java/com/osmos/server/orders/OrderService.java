package com.osmos.server.orders;

import com.osmos.server.orders.dto.FullOrderDto;
import com.osmos.server.orders.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;


    public void delete() {}

    public boolean create(FullOrderDto fullOrderDto) {

        return true;
    }

    public List<FullOrderDto> getPage(int pageNumber, int pageSize) {
        List<Order> orderList = orderRepo.findAll(PageRequest.of(pageNumber, pageSize)).getContent();
        return orderList.stream().map(order -> FullOrderDto.builder()
                .orderedBy(order.getOrderedBy().getId().toString())
                .location(order.getLocation())
                .finalPrice(order.getFinalPrice())
//                .productList(order.getProductList().stream().map().toList())
                .build()).toList();
    }

}
