package com.vshtd.parceldelivery.logistic.model.internal;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@Builder
public class GetCourierOrdersIn implements ICourier {

    private String courierName;
    private Pageable pageable;
}
