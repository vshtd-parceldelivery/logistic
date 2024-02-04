package com.vshtd.parceldelivery.logistic.operation;

import com.vshtd.parceldelivery.logistic.action.GetOrderByUserAndUuidAction;
import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderRespDTO;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.internal.UpdateOrderByUserInO;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.vshtd.parceldelivery.logistic.model.mqo.TrackEventType.DESTINATION_CHANGED;

@Component
public class UpdateOrderByUserOperation extends BaseTrackableOperation<UpdateOrderByUserInO, ParcelOrderRespDTO> {

    @Autowired
    private GetOrderByUserAndUuidAction getOrder;

    @Transactional
    @Override
    public ParcelOrderRespDTO execute(UpdateOrderByUserInO req) {
        ParcelOrder order = getOrder.execute(req, getCtx());
        checkNoOrderException(order, req);
        if (order.cantUserChangeDestination()) {
            throw new RuntimeException("You can no longer change destination of order!");
        }
        order.setDestination(req.getDestination());
        return respOrderMapper.map(parcelOrderRepository.save(order));
    }

    @Override
    protected void postOperation(UpdateOrderByUserInO req) {
        trackOrder.execute(ImmutablePair.of(getCtx().getOrder(), DESTINATION_CHANGED), getCtx());
    }
}
