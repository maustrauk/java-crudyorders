package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Payment;
import com.lambdaschool.crudyorders.repositories.PaymentsRepository;
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
