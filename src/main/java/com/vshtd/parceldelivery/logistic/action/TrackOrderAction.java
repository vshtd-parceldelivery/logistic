package com.vshtd.parceldelivery.logistic.action;

import com.vshtd.parceldelivery.logistic.model.ctx.Ctx;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.mqo.TrackEventType;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

@Component
public class TrackOrderAction extends BaseTrackableAction<Pair<ParcelOrder, TrackEventType>, Void> {

    @Override
    public Void execute(Pair<ParcelOrder, TrackEventType> req, Ctx ctx) {
        if (req.getLeft().isNotify()) {
            rmqSender.trackOrder(trackEventMapper.map(ImmutablePair.of(req.getLeft(), req.getRight())));
        }
        return null;
    }
}
