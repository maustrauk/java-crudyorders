package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.services.CustomersService;
import com.lambdaschool.orders.views.CustomerCountOrders;
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
@RequestMapping("/customers")
public class CustomersController {
    @Autowired
    private CustomersService customersService;

    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<?> listAllCustomers() {
        List<Customer> myCustomers = customersService.findAllCustomers();
        return new ResponseEntity<>(myCustomers, HttpStatus.OK);
    }

    @GetMapping (value = "/customer/{customerId}", produces = "application/json")
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

    @DeleteMapping(value = "/customer/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long id) {
        customersService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*@PatchMapping(value = "/customer/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer updateCustomer, @PathVariable long id) {
        updateCustomer = customersService.update(updateCustomer, id);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }*/

    /*@PostMapping(value = "/customer", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> addCustomer(@Valid @RequestBody Customer newCustomer) {
        newCustomer.setCustcode(0);
        newCustomer = customersService.save(newCustomer);

        HttpHeaders responseHeaders = new HttpHeaders();

        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{custcode}")
                .buildAndExpand(newCustomer.getCustcode())
                .toUri();
        responseHeaders.setLocation(newCustomerURI);

        return new ResponseEntity<>(newCustomer, responseHeaders, HttpStatus.CREATED);
    }*/

    @PutMapping(value = "/customer/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> updateFullCustumer(@Valid @RequestBody Customer updateCustomer, @PathVariable long id){
        updateCustomer.setCustcode(id);
        updateCustomer = customersService.save(updateCustomer);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }
}
