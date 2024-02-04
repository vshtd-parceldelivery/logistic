package com.vshtd.parceldelivery.logistic.action;

import com.vshtd.parceldelivery.logistic.model.ctx.Ctx;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.internal.IBothCourierOrder;
import org.springframework.stereotype.Component;

@Component
public class GetOrderByCourierAndUuidAction extends BaseAction<IBothCourierOrder, ParcelOrder> {

    @Override
    public ParcelOrder execute(IBothCourierOrder req, Ctx ctx) {
        return ctx.setOrder(parcelOrderRepository.findByCourierAndUuid(req.getCourierName(), req.getOrderUuid()));
    }
}
