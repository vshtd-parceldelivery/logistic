package com.vshtd.parceldelivery.logistic.model.internal;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@Builder
public class GetUserOrdersInO implements IUser {

    private String username;
    private Pageable pageable;
}
