package com.bluethinkInc.payment_services.service;


import com.bluethinkInc.payment_services.dto.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentConsumer {
    @KafkaListener(
            topics = "order-created",
            groupId = "payment-group"
    )
    public void consume(OrderEvent event){
        log.info(
                "Received Order event",
                event
        );
        processPayment(event);
    }
    public void processPayment(OrderEvent event ){
        System.out.println(
                "Payment processing......"
        );
        log.info(
                "Payment processing for order id: {}",
                event.getOrderId()
        );
    }
}
