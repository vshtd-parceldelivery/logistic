package com.vshtd.parceldelivery.logistic.operation;

import com.vshtd.parceldelivery.logistic.action.GetOrderAction;
import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderRespDTO;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.internal.ChangeOrderStatusInO;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.vshtd.parceldelivery.logistic.model.mqo.TrackEventType.STATUS_CHANGED;

@Component
public class ChangeOrderStatusOperation extends BaseTrackableOperation<ChangeOrderStatusInO, ParcelOrderRespDTO> {

    @Autowired
    private GetOrderAction getOrder;

    @Transactional
    @Override
    public ParcelOrderRespDTO execute(ChangeOrderStatusInO req) {
        ParcelOrder order = getOrder.execute(req, getCtx());
        checkNoOrderException(order, req);
        order.setStatus(req.getStatus());
        return respOrderMapper.map(parcelOrderRepository.save(order));
    }

    @Override
    protected void postOperation(ChangeOrderStatusInO req) {
        trackOrder.execute(ImmutablePair.of(getCtx().getOrder(), STATUS_CHANGED), getCtx());
    }
}
