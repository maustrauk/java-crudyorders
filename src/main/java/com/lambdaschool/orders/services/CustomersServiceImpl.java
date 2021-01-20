package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.AgentsRepository;
import com.lambdaschool.orders.repositories.CustomersRepository;
import com.lambdaschool.orders.repositories.OrdersRepository;
import com.lambdaschool.orders.repositories.PaymentsRepository;
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

    @Autowired
    private AgentsRepository agentsrepos;

    @Autowired
    private OrdersRepository ordersrepos;

    @Autowired
    private PaymentsRepository paymentsrepo;

    @Transactional
    @Override
    public Customer save(Customer customer) {

        Customer newCustomer = new Customer();

        if(customer.getCustcode() != 0) {
            customersrepos.findById(customer.getCustcode())
                    .orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " not found!"));
            newCustomer.setCustcode(customer.getCustcode());
        }

        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPhone(customer.getPhone());

        Agent newAgent = agentsrepos.findById(customer.getAgent().getAgentcode())
                .orElseThrow(() -> new EntityNotFoundException("Agent " + customer.getAgent().getAgentcode() + " not found!"));
        newCustomer.setAgent(newAgent);

        newCustomer.getOrders().clear();
        for (Order o: customer.getOrders()) {
            Order newOrder = new Order();

            if(o.getOrdnum() != 0) {
                ordersrepos.findById(o.getOrdnum())
                        .orElseThrow(() -> new EntityNotFoundException("Order " + o.getOrdnum() +" not found!"));
                newOrder.setOrdnum(o.getOrdnum());
            }

            newOrder.setOrdamount(o.getOrdamount());
            newOrder.setAdvanceamount(o.getAdvanceamount());
            newOrder.setOrderdescription(o.getOrderdescription());
            newOrder.setCustomer(newCustomer);

            newOrder.getPayments().clear();
            for (Payment p: o.getPayments()) {
                Payment newPay = paymentsrepo.findById(p.getPaymentid())
                        .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " not found!"));
                newOrder.getPayments().add(newPay);
            }

            newCustomer.getOrders().add(newOrder);
        }

        return customersrepos.save(newCustomer);
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

    @Transactional
    @Override
    public void delete(long id) {
        if (customersrepos.findById(id).isPresent()) {
            customersrepos.deleteById(id);
        } else {
            throw new EntityNotFoundException("Customer " + id + " not found!");
        }
    }
}
