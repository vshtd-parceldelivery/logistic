package com.vshtd.parceldelivery.logistic.action;

import com.vshtd.parceldelivery.logistic.distribution.rmq.RmqSender;
import com.vshtd.parceldelivery.logistic.mapper.TrackEventMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseTrackableAction<REQ, RESP> extends BaseAction<REQ, RESP> {

    @Autowired
    protected RmqSender rmqSender;

    @Autowired
    protected TrackEventMapper trackEventMapper;
}
