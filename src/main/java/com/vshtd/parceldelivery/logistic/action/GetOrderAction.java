package com.vshtd.parceldelivery.logistic.action;

import com.vshtd.parceldelivery.logistic.model.ctx.Ctx;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.internal.IOrder;
import org.springframework.stereotype.Component;

@Component
public class GetOrderAction extends BaseAction<IOrder, ParcelOrder> {

    @Override
    public ParcelOrder execute(IOrder req, Ctx ctx) {
        return ctx.setOrder(parcelOrderRepository.findByUuid(req.getOrderUuid()));
    }
}
