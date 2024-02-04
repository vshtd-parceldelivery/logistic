package com.vshtd.parceldelivery.logistic.distribution.rmq;

import com.vshtd.parceldelivery.logistic.model.mqo.ParcelOrderTrackMQO;

public interface RmqSender {

    void trackOrder(ParcelOrderTrackMQO trackOrder);
}
