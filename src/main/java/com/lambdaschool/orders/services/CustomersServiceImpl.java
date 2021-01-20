package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.repositories.CustomersRepository;
import com.lambdaschool.orders.views.CustomerCountOrders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customersService")
public class CustomersServiceImpl implements CustomersService{
    @Autowired
    private CustomersRepository customersrepos;

    @Transactional
    @Override
    public Customer save(Customer customer) {
        return customersrepos.save(customer);
    }

    @Override
    public List<Customer> findAllCustomers() {
        List<Customer> list = new ArrayList<>();

        customersrepos.findAll().iterator().forEachRemaining(list::add);

        return list;
    }

    @Override
    public Customer findCustomerById(long id)
        throws EntityNotFoundException {
        return customersrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found"));
    }

    @Override
    public List<Customer> findCustomerByNameLike (String namelike) {
        List<Customer> list = customersrepos.findByCustnameContainingIgnoringCase(namelike);
        return list;
    }

    @Override
    public List<CustomerCountOrders> getCustomerCountOrders()
    {
        return customersrepos.getCountOrders();
    }
}
