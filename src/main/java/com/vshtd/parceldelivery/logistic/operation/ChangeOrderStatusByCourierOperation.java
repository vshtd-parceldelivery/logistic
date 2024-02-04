package com.vshtd.parceldelivery.logistic.operation;

import com.vshtd.parceldelivery.logistic.action.GetOrderByCourierAndUuidAction;
import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderRespDTO;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.internal.ChangeOrderStatusByCourierInO;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.vshtd.parceldelivery.logistic.model.mqo.TrackEventType.STATUS_CHANGED;

@Component
public class ChangeOrderStatusByCourierOperation extends BaseTrackableOperation<ChangeOrderStatusByCourierInO, ParcelOrderRespDTO> {

    @Autowired
    private GetOrderByCourierAndUuidAction getOrder;

    @Transactional
    @Override
    public ParcelOrderRespDTO execute(ChangeOrderStatusByCourierInO req) {
        ParcelOrder order = getOrder.execute(req, getCtx());
        checkNoOrderException(order, req);
        if (order.cantCourierChangeStatus()) {
            throw new RuntimeException("You can't change order status!");
        }
        order.setStatus(req.getStatus());
        return respOrderMapper.map(parcelOrderRepository.save(order));
    }

    @Override
    protected void postOperation(ChangeOrderStatusByCourierInO req) {
        trackOrder.execute(ImmutablePair.of(getCtx().getOrder(), STATUS_CHANGED), getCtx());
    }
}
