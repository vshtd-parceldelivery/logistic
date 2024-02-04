package com.vshtd.parceldelivery.logistic.model.internal;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class UpdateOrderByUserInO implements IBothUserOrder {

    private UUID orderUuid;
    private String destination;
    private String username;

}
