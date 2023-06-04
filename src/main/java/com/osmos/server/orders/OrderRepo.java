package com.osmos.server.orders;

import com.osmos.server.orders.entities.Order;
import com.osmos.server.schema.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<Order, UUID> {

    Page<Order> findAll(Pageable pageable);

    List<Order> findAllByOrderedBy(User user);

    Optional<Order> findByClientSecret(String clientSecret);

}
