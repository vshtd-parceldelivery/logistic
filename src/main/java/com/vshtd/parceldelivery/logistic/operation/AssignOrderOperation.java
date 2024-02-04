package com.vshtd.parceldelivery.logistic.operation;

import com.vshtd.parceldelivery.logistic.action.GetOrderAction;
import com.vshtd.parceldelivery.logistic.action.GetOrderByCourierAndUuidAction;
import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderRespDTO;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.internal.AssignOrderInO;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.vshtd.parceldelivery.logistic.model.mqo.TrackEventType.COURIER_ASSIGNED;

@Component
public class AssignOrderOperation extends BaseTrackableOperation<AssignOrderInO, ParcelOrderRespDTO> {

    @Autowired
    private GetOrderByCourierAndUuidAction getCourierOrder;

    @Autowired
    private GetOrderAction getOrder;

    @Override
    protected void preOperation(AssignOrderInO req) {
        ParcelOrder order = getCourierOrder.execute(req, getCtx());
        if (Objects.nonNull(order)) {
            order.updateDate();
            order.doNotNotify();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ParcelOrderRespDTO execute(AssignOrderInO req) {
        ParcelOrder order = getCtx().getOrder();
        if (Objects.isNull(order)) {
            order = getOrder.execute(req, getCtx());
            checkNoOrderException(order, req);
            order.setCourier(req.getCourierName());
        }
        return respOrderMapper.map(parcelOrderRepository.save(order));
    }

    @Override
    protected void postOperation(AssignOrderInO req) {
        trackOrder.execute(ImmutablePair.of(getCtx().getOrder(), COURIER_ASSIGNED), getCtx());
    }
}
