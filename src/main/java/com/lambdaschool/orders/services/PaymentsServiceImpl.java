package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "paymentsService")
public class PaymentsServiceImpl implements PaymentsService{
    @Autowired
    private PaymentsRepository paymentsrepo;

    @Transactional
    @Override
    public Payment save(Payment payment) {
        return paymentsrepo.save(payment);
    }
}
