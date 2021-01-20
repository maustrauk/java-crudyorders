package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.CustomersRepository;
import com.lambdaschool.orders.repositories.OrdersRepository;
import com.lambdaschool.orders.repositories.PaymentsRepository;
import com.lambdaschool.orders.views.CustomerCountOrders;
import com.lambdaschool.orders.views.OrdersWithCustomersAdvam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Transactional
@Service(value = "ordersService")
public class OrdersServiceImpl implements OrdersService{
    @Autowired
    private OrdersRepository ordersrepo;

    @Autowired
    private CustomersRepository customersrepo;

    @Autowired
    private PaymentsRepository paymentsrepo;

    @Transactional
    @Override
    public Order save(Order order) {

        Order newOrder = new Order();

        if(order.getOrdnum() != 0) {
            ordersrepo.findById(order.getOrdnum())
                    .orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " not found!"));
            newOrder.setOrdnum(order.getOrdnum());
        }

        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setOrderdescription(order.getOrderdescription());

        Customer newCustomer = customersrepo.findById(order.getCustomer().getCustcode())
                .orElseThrow(() -> new EntityNotFoundException("Customer " + order.getCustomer().getCustcode() + " not found!"));
        newOrder.setCustomer(newCustomer);

        newOrder.getPayments().clear();
        for (Payment p: order.getPayments()) {
            Payment newPay = paymentsrepo.findById(p.getPaymentid())
                    .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " not found!"));
            newOrder.getPayments().add(newPay);
        }

        return ordersrepo.save(newOrder);
    }

    @Override
    public Order findOrderById(long id)
        throws EntityNotFoundException {
        return ordersrepo.findById(id)
                .orElseThrow(() ->new EntityNotFoundException("Order " + id + " Not Found"));
    }

    @Override
    public List<OrdersWithCustomersAdvam> getOrdersWithCustomersAdvam() {
        return ordersrepo.getOrdersWithCustomersAdvam();
    }

    @Transactional
    @Override
    public void delete(long id) {
        if (ordersrepo.findById(id).isPresent()) {
            ordersrepo.deleteById(id);
        } else {
            throw new EntityNotFoundException("Order " + id + " not found!");
        }
    }
}
