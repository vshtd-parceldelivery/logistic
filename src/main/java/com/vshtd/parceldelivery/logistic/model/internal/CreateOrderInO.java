package com.vshtd.parceldelivery.logistic.model.internal;

import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderDTO;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateOrderInO {

    private ParcelOrderDTO parcelOrder;
    private String name;
}
