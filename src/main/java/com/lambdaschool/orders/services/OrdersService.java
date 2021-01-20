package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.views.CustomerCountOrders;
import com.lambdaschool.orders.views.OrdersWithCustomersAdvam;

import java.util.List;

public interface OrdersService {
    Order save(Order order);
    Order findOrderById(long id);
    List<OrdersWithCustomersAdvam> getOrdersWithCustomersAdvam();
}
