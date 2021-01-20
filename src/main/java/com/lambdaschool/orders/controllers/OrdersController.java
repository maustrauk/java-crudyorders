package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.services.OrdersService;
import com.lambdaschool.orders.views.CustomerCountOrders;
import com.lambdaschool.orders.views.OrdersWithCustomersAdvam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @GetMapping(value = "/order/{orderId}", produces = "application/json")
    public ResponseEntity<?> getOrderByIdWithoutPayment (@PathVariable Long orderId) {
        Order o = ordersService.findOrderById(orderId);

        return new ResponseEntity<>(o, HttpStatus.OK);
    }

    @GetMapping(value = "/advanceamount", produces = "application/json")
    public ResponseEntity<?> getAdvanceamount() {
        List<OrdersWithCustomersAdvam> myList = ordersService.getOrdersWithCustomersAdvam();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    @DeleteMapping(value = "/order/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable long id) {
        ordersService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
