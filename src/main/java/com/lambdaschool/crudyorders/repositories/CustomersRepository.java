package com.lambdaschool.crudyorders.repositories;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.views.CustomerCountOrders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomersRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByCustnameContainingIgnoringCase(String namelike);

    @Query(value = "SELECT c.custname AS customer_name, " +
            "COUNT(ordnum) AS count_of_orders " +
            "FROM customers c LEFT JOIN orders o " +
            "ON c.custcode = o.custcode " +
            "GROUP BY customer_name " +
            "ORDER BY count_of_orders DESC ",
            nativeQuery = true)
    List<CustomerCountOrders> getCountOrders();
}
