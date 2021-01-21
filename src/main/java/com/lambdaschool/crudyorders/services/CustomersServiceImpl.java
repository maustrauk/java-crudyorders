package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Agent;
import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.models.Payment;
import com.lambdaschool.crudyorders.repositories.AgentsRepository;
import com.lambdaschool.crudyorders.repositories.CustomersRepository;
import com.lambdaschool.crudyorders.repositories.OrdersRepository;
import com.lambdaschool.crudyorders.repositories.PaymentsRepository;
import com.lambdaschool.crudyorders.views.CustomerCountOrders;
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

    @Transactional
    @Override
    public Customer update(Customer updateCustomer, long id) {

        Customer currentCustomer = customersrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " not found!"));

        if (updateCustomer.getCustname() != null) {
            currentCustomer.setCustname(updateCustomer.getCustname());
        }

        if (updateCustomer.getCustcity() != null) {
            currentCustomer.setCustcity(updateCustomer.getCustcity());
        }

        if(updateCustomer.getWorkingarea() != null) {
            currentCustomer.setWorkingarea(updateCustomer.getWorkingarea());
        }

        if(updateCustomer.getCustcountry() != null) {
            currentCustomer.setCustcountry(updateCustomer.getCustcountry());
        }

        if(updateCustomer.getGrade() != null) {
            currentCustomer.setGrade(updateCustomer.getGrade());
        }

        if(updateCustomer.hasvalueforopeningamt) {
            currentCustomer.setOpeningamt(updateCustomer.getOpeningamt());
        }

        if(updateCustomer.hasvalueforreceiveamt) {
            currentCustomer.setReceiveamt(updateCustomer.getReceiveamt());
        }

        if(updateCustomer.hasvalueforpaymentamt) {
            currentCustomer.setPaymentamt(updateCustomer.getPaymentamt());
        }

        if(updateCustomer.hasvalueforoutstandingamt) {
            currentCustomer.setOutstandingamt(updateCustomer.getOutstandingamt());
        }

        if(updateCustomer.getPhone() != null) {
            currentCustomer.setPhone(updateCustomer.getPhone());
        }


        Agent newAgent = agentsrepos.findById(updateCustomer.getAgent().getAgentcode())
                .orElseThrow(() -> new EntityNotFoundException("Agent " + updateCustomer.getAgent().getAgentcode() + " not found!"));
        currentCustomer.setAgent(newAgent);

        if(updateCustomer.getOrders().size() > 0) {
            currentCustomer.getOrders().clear();
            for (Order o : updateCustomer.getOrders()) {
               Order currentOrder = ordersrepos.findById(o.getOrdnum())
                       .orElseThrow(() -> new EntityNotFoundException("Order " + o.getOrdnum() + " not found!"));

               if(o.hasvalueforordamount) {
                   currentOrder.setOrdamount(o.getOrdamount());
               }

               if(o.hasvalueforadvanceamount) {
                   currentOrder.setAdvanceamount(o.getAdvanceamount());
               }

               if(o.getOrderdescription() != null) {
                   currentOrder.setOrderdescription(o.getOrderdescription());
               }
                currentOrder.setCustomer(currentCustomer);

               if(o.getPayments().size() > 0) {
                   currentOrder.getPayments().clear();
                   for (Payment p : o.getPayments()) {
                       Payment newPay = paymentsrepo.findById(p.getPaymentid())
                               .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " not found!"));
                       currentOrder.getPayments().add(newPay);
                   }
               }

                currentCustomer.getOrders().add(currentOrder);
            }
        }

        return customersrepos.save(currentCustomer);
    }
}
