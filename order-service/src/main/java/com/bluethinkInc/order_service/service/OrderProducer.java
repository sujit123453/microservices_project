package com.bluethinkInc.order_service.service;

import com.bluethinkInc.order_service.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {
   private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

   public void publishOrderEvent(OrderEvent event){
       kafkaTemplate.send("order-events",
               event);
   }
}
