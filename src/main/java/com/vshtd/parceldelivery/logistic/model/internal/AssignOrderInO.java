package com.vshtd.parceldelivery.logistic.model.internal;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class AssignOrderInO implements IBothCourierOrder {

    private UUID orderUuid;
    private String courierName;
}
