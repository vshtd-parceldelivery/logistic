package com.vshtd.parceldelivery.logistic.mapper;

import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderRespDTO;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import org.springframework.stereotype.Component;

@Component
public class RespOrderMapper implements Mapper<ParcelOrder, ParcelOrderRespDTO> {

    @Override
    public ParcelOrderRespDTO map(ParcelOrder order) {
        return ParcelOrderRespDTO.builder()
                .uuid(order.getUuid())
                .username(order.getAuthor())
                .status(order.getStatus())
                .type(order.getType())
                .courier(order.getCourier())
                .description(order.getDescription())
                .destination(order.getDestination())
                .creationDate(order.getCreationDate())
                .updateDate(order.getUpdateDate())
                .expectedDeliveryDate(order.getExpectedDeliveryDate())
                .build();
    }
}
