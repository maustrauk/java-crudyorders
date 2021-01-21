package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.views.OrdersWithCustomersAdvam;

import java.util.List;

public interface OrdersService {
    Order save(Order order);
    Order findOrderById(long id);
    List<OrdersWithCustomersAdvam> getOrdersWithCustomersAdvam();
    void delete (long id);
}
