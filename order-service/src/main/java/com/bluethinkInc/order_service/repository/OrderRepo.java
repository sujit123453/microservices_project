package com.bluethinkInc.order_service.repository;

import com.bluethinkInc.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
}
