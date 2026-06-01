package com.bluethinkInc.payment_services.repository;

import com.bluethinkInc.payment_services.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepo extends JpaRepository<Payment,Long> {
}
