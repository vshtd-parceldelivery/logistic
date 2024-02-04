package com.vshtd.parceldelivery.logistic.operation;

import com.vshtd.parceldelivery.logistic.action.GetOrderByUserAndUuidAction;
import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderRespDTO;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.internal.CancelOrderInO;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.vshtd.parceldelivery.logistic.model.entity.ParcelOrderStatus.CANCELLED;
import static com.vshtd.parceldelivery.logistic.model.mqo.TrackEventType.STATUS_CHANGED;

@Component
public class CancelOrderOperation extends BaseTrackableOperation<CancelOrderInO, ParcelOrderRespDTO> {

    @Autowired
    private GetOrderByUserAndUuidAction getOrder;

    @Transactional
    @Override
    public ParcelOrderRespDTO execute(CancelOrderInO req) {
        ParcelOrder order = getOrder.execute(req, getCtx());
        checkNoOrderException(order, req);
        if (order.cantUserChangeStatus()) {
            throw new RuntimeException("You can no longer cancel order!");
        }
        order.setStatus(CANCELLED);
        return respOrderMapper.map(parcelOrderRepository.save(order));
    }

    @Override
    protected void postOperation(CancelOrderInO req) {
        trackOrder.execute(ImmutablePair.of(getCtx().getOrder(), STATUS_CHANGED), getCtx());
    }
}
