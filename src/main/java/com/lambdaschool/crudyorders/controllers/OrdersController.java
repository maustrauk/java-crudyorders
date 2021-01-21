package com.lambdaschool.crudyorders.controllers;

import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.services.OrdersService;
import com.lambdaschool.crudyorders.views.OrdersWithCustomersAdvam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

    @PutMapping(value="/order/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> updateFullOrder(@Valid @RequestBody Order updateOrder, @PathVariable long id){
        updateOrder.setOrdnum(id);
        updateOrder = ordersService.save(updateOrder);
        return new ResponseEntity<>(updateOrder, HttpStatus.OK);
    }
    @PostMapping(value="order", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> addOrder(@Valid @RequestBody Order newOrder) {
       newOrder.setOrdnum(0);
       newOrder = ordersService.save(newOrder);

        HttpHeaders responseHeaders = new HttpHeaders();

        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{ordnum}")
                .buildAndExpand(newOrder.getOrdnum())
                .toUri();
        responseHeaders.setLocation(newOrderURI);

        return new ResponseEntity<>(newOrder, responseHeaders, HttpStatus.CREATED);
    }
}
