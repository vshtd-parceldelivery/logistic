package com.vshtd.parceldelivery.logistic.mapper;

import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderDTO;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrderStatus;
import com.vshtd.parceldelivery.logistic.util.Generator;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CreateOrderMapper implements Mapper<ParcelOrderDTO, ParcelOrder> {

    @Override
    public ParcelOrder map(ParcelOrderDTO order) {
        return ParcelOrder.builder()
                .description(order.getDescription())
                .destination(order.getDestination())
                .uuid(Generator.uuid())
                .type(order.getType())
                .status(ParcelOrderStatus.CREATED)
                .creationDate(new Date())
                .updateDate(new Date())
                .build();
    }
}
