package com.vshtd.parceldelivery.logistic.model.dto;

import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrderStatus;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrderType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ParcelOrderRespDTO implements Serializable {

    private UUID uuid;
    private String username;
    private String description;
    private String destination;
    private ParcelOrderStatus status;
    private ParcelOrderType type;
    private String courier;
    private Date creationDate;
    private Date updateDate;
    private Date expectedDeliveryDate;
}
