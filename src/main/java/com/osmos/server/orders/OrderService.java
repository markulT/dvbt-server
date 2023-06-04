package com.osmos.server.orders;

import com.osmos.server.orders.dto.CreateOrderDto;
import com.osmos.server.orders.dto.FullOrderDto;
import com.osmos.server.orders.dto.OrderDto;
import com.osmos.server.orders.dto.OrderItemDto;
import com.osmos.server.orders.entities.Order;
import com.osmos.server.orders.entities.OrderItem;
import com.osmos.server.orders.entities.OrderStatus;
import com.osmos.server.orders.entities.PaymentStatus;
import com.osmos.server.orders.exceptions.UserIsNotEngineer;
import com.osmos.server.orders.exceptions.orderCreationExceptions.OrderCreationException;
import com.osmos.server.products.ProductsRepo;
import com.osmos.server.products.entities.Product;
import com.osmos.server.repo.RoleRepo;
import com.osmos.server.repo.UserRepo;
import com.osmos.server.roles.RoleService;
import com.osmos.server.schema.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepo orderRepo;
    private final ProductsRepo productsRepo;
    private final UserRepo userRepo;
    private final RoleService roleService;
    private final OrderItemRepo orderItemRepo;


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
        try {
            var currentUser = SecurityContextHolder.getContext().getAuthentication();
//        List<Product> productList = fullOrderDto.getProductList().stream().map(productId->productsRepo.getProductById(UUID.fromString(productId))).toList();
            List<OrderItemDto> productList = fullOrderDto.getProductList();
            List<OrderItem> orderItemList = productList.stream().map(product -> {
                OrderItem orderItem = OrderItem.builder()
                        .quantity(product.getQuantity())
                        .product(productsRepo.getProductById(UUID.fromString(product.getProductId())))
                        .build();
                return orderItem;
            }).toList();

            System.out.println(orderItemList.size());
            Order order = Order.builder()
                    .location(fullOrderDto.getLocation())
                    .finalPrice(productList.stream().mapToDouble(this::countOrdersFinalPrice).sum())
                    .orderedBy(userRepo.getUserByEmail(currentUser.getName()))
                    .paymentStatus(PaymentStatus.UNKNOWN)
                    .build();
            order.setProductList(orderItemList.stream().map(orderItem -> {
                orderItem.setOrder(order);
                return orderItem;
            }).collect(Collectors.toCollection(ArrayList::new)));
            log.info("Product list is : ");
            System.out.println(order.getProductList().getClass());
            Order order1 = orderRepo.save(order);
            System.out.println(order1.getId().toString());
            return FullOrderDto.copyFromEntity(order1);
        } catch (RuntimeException e) {
            System.out.println("error");
            log.error(e.getMessage(), e.getCause());
            log.error(String.valueOf(e.getStackTrace()));
            throw new OrderCreationException("Error while creating");
        }
    }

    public void setClientSecretToOrder(String clientSecret, String orderId) throws OrderCreationException {
        try {
            Order order = orderRepo.findById(UUID.fromString(orderId)).orElseThrow();

            order.setClientSecret(clientSecret);
            orderRepo.save(order);
        } catch (NoSuchElementException e) {
            throw new OrderCreationException("Error at setting client secret to order (because the order does not exist)");
        }
    }

    public double countOrdersFinalPrice(OrderItemDto orderItemDto) {
        return orderItemDto.getQuantity() * productsRepo.getProductById(UUID.fromString(orderItemDto.getProductId())).getPrice();
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

    public FullOrderDto getOrderDetails(String id) {
        Order order = orderRepo.findById(UUID.fromString(id)).orElseThrow();
        return FullOrderDto.copyFromEntity(order);
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
