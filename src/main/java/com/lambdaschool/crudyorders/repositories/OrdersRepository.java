package com.lambdaschool.crudyorders.repositories;

import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.views.OrdersWithCustomersAdvam;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrdersRepository extends CrudRepository<Order, Long> {
    @Query(value = "SELECT c.custname AS customer_name, " +
            "c.custcity AS customer_city, " +
            "c.custcountry AS customer_country, " +
            "c.workingarea AS customer_area, " +
            "c.phone AS customer_phone, " +
            "c.grade AS customer_grade, " +
            "o.advanceamount AS order_advance_amount, " +
            "o.ordamount AS order_amount, " +
            "o.orderdescription AS order_description " +
            "FROM orders o LEFT JOIN customers c " +
            "ON o.custcode = c.custcode " +
            "WHERE o.advanceamount > 0 ",
         nativeQuery = true)
    List<OrdersWithCustomersAdvam> getOrdersWithCustomersAdvam();

    Order findByOrdamount(double amount);
}
