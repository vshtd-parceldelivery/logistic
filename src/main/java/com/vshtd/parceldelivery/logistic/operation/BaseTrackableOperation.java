package com.vshtd.parceldelivery.logistic.operation;

import com.vshtd.parceldelivery.logistic.action.TrackOrderAction;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseTrackableOperation<REQ, RESP> extends BaseOperation<REQ, RESP> {

    @Autowired
    protected TrackOrderAction trackOrder;
}
