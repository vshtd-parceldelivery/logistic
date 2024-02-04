package com.vshtd.parceldelivery.logistic.distribution.rmq;

import com.vshtd.parceldelivery.logistic.model.mqo.ParcelOrderTrackMQO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RmqSenderImpl implements RmqSender {

    @Value("${rabbitmq.track-order.rk}")
    private String trackOrderRk;

    @Autowired
    private Exchange trackOrderExchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void trackOrder(ParcelOrderTrackMQO trackOrder) {
        log.info("RMQ sending | track order | uuid: {} | ex: {} | recipients: {} ",
                trackOrder.getOrderUuid(), trackOrderExchange.getName(), trackOrder.getRecipients());
        rabbitTemplate.convertAndSend(trackOrderExchange.getName(), trackOrderRk, trackOrder);
    }
}
