package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.views.CustomerCountOrders;

import java.util.List;

public interface CustomersService {
    Customer save(Customer customer);
    List<Customer> findAllCustomers();
    Customer findCustomerById(long id);
    List<Customer> findCustomerByNameLike(String namelike);
    List<CustomerCountOrders> getCustomerCountOrders();
    void delete(long id);

    Customer update(Customer updateCustomer, long id);
}
