package com.vshtd.parceldelivery.logistic.action;

import com.vshtd.parceldelivery.logistic.repository.ParcelOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseAction<REQ, RESP> implements Action<REQ, RESP> {

    @Autowired
    protected ParcelOrderRepository parcelOrderRepository;

}
