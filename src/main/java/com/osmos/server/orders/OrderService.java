package com.osmos.server.orders;

import com.osmos.server.orders.dto.CreateOrderDto;
import com.osmos.server.orders.dto.FullOrderDto;
import com.osmos.server.orders.dto.OrderDto;
import com.osmos.server.orders.entities.Order;
import com.osmos.server.orders.entities.OrderStatus;
import com.osmos.server.orders.exceptions.UserIsNotEngineer;
import com.osmos.server.products.ProductsRepo;
import com.osmos.server.products.entities.Product;
import com.osmos.server.repo.RoleRepo;
import com.osmos.server.repo.UserRepo;
import com.osmos.server.roles.RoleService;
import com.osmos.server.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final ProductsRepo productsRepo;
    private final UserRepo userRepo;
    private final RoleService roleService;



    public boolean deleteOrder(String id) {
        Order order = orderRepo.findById(UUID.fromString(id)).orElseThrow();
        orderRepo.delete(order);
        return true;
    }

    public FullOrderDto updateStatus(String orderId, OrderStatus status) {
        Order orderToUpdate = orderRepo.findById(UUID.fromString(orderId)).orElseThrow();
        orderToUpdate.setOrderStatus(status);
        orderRepo.save(orderToUpdate);
        return FullOrderDto.copyFromEntity(orderToUpdate);
    }

    public long getLength() {
        return orderRepo.count();
    }

    public FullOrderDto create(CreateOrderDto fullOrderDto) {

        var currentUser = SecurityContextHolder.getContext().getAuthentication();
        List<Product> productList = fullOrderDto.getProductList().stream().map(productId->productsRepo.getProductById(UUID.fromString(productId))).toList();
        Order order = Order.builder()
                .location(fullOrderDto.getLocation())
                .finalPrice(productList.stream().mapToDouble(Product::getPrice).sum())
                .orderedBy(userRepo.getUserByEmail(currentUser.getName()))
                .productList(productList)
                .build();
        orderRepo.save(order);
        return FullOrderDto.copyFromEntity(order);

    }


    public List<OrderDto> getPage(int pageNumber, int pageSize) {
        List<Order> orderList = orderRepo.findAll(PageRequest.of(pageNumber - 1, pageSize)).getContent();
        System.out.println(orderList);
        return orderList.stream().map(OrderDto::copyFromEntity).toList();
    }

    public OrderDto getOrder(String id) {

        Order order = orderRepo.findById(UUID.fromString(id)).orElseThrow();
        return OrderDto.copyFromEntity(order);
    }

    public FullOrderDto assignEngineerToOrder(String orderId, String engineerId) {
        Order order = orderRepo.findById(UUID.fromString(orderId)).orElseThrow();
        User engineer = userRepo.findById(UUID.fromString(engineerId)).orElseThrow();
        if (!roleService.isUserEngineer(engineer)) {
            throw new UserIsNotEngineer("This user is not engineer");
        }
        order.setEngineer(engineer);
        orderRepo.save(order);
        return FullOrderDto.copyFromEntity(order);
    }


}
