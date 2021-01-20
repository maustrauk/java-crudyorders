package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.services.CustomersService;
import com.lambdaschool.orders.views.CustomerCountOrders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomersController {
    @Autowired
    private CustomersService customersService;

    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<?> listAllCustomers() {
        List<Customer> myCustomers = customersService.findAllCustomers();
        return new ResponseEntity<>(myCustomers, HttpStatus.OK);
    }

    @GetMapping (value = "/customers/{customerId}", produces = "application/json")
    public ResponseEntity<?> getCustomerById (@PathVariable Long customerId) {
        Customer c = customersService.findCustomerById(customerId);

        return new ResponseEntity<>(c, HttpStatus.OK);
    }

    @GetMapping (value = "/namelike/{restname}", produces = "application/json")
    public  ResponseEntity<?> findCustomerByNameLike (@PathVariable String restname) {
        List<Customer> rtnList = customersService.findCustomerByNameLike(restname);
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    @GetMapping(value = "/orders/count", produces = "application/json")
    public ResponseEntity<?> getCountCustomerOrders() {
        List<CustomerCountOrders> myList = customersService.getCustomerCountOrders();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }
}
