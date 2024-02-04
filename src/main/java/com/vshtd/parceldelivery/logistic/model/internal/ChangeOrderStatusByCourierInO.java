package com.vshtd.parceldelivery.logistic.model.internal;

import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrderStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ChangeOrderStatusByCourierInO implements IBothCourierOrder {

    private UUID orderUuid;
    private ParcelOrderStatus status;
    private String courierName;
}
