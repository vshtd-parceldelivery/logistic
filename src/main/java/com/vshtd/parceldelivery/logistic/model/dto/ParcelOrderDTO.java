package com.vshtd.parceldelivery.logistic.model.dto;

import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrderType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class ParcelOrderDTO implements Serializable {

    private String description;
    private String destination;
    private ParcelOrderType type;
}
