package com.vshtd.parceldelivery.logistic.model.internal;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class GetOrderInO implements IOrder {

    private UUID orderUuid;
}
