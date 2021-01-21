package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Payment;

public interface PaymentsService {
    Payment save(Payment payment);
}
