package com.vshtd.parceldelivery.logistic.action;

import com.vshtd.parceldelivery.logistic.model.ctx.Ctx;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.internal.IBothUserOrder;
import org.springframework.stereotype.Component;

@Component
public class GetOrderByUserAndUuidAction extends BaseAction<IBothUserOrder, ParcelOrder> {

    @Override
    public ParcelOrder execute(IBothUserOrder req, Ctx ctx) {
        return ctx.setOrder(parcelOrderRepository.findByAuthorAndUuid(req.getUsername(), req.getOrderUuid()));
    }
}
