package com.vshtd.parceldelivery.logistic.action;

import com.vshtd.parceldelivery.logistic.mapper.CreateOrderMapper;
import com.vshtd.parceldelivery.logistic.model.ctx.Ctx;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.internal.CreateOrderInO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderAction extends BaseAction<CreateOrderInO, ParcelOrder> {

    @Autowired
    private CreateOrderMapper createOrderMapper;

    @Override
    public ParcelOrder execute(CreateOrderInO req, Ctx ctx) {
        ParcelOrder order = createOrderMapper.map(req.getParcelOrder());
        order.setAuthor(req.getName());
        return ctx.setOrder(order);
    }
}
