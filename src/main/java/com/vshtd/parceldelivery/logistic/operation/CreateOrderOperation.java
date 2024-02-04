package com.vshtd.parceldelivery.logistic.operation;

import com.vshtd.parceldelivery.logistic.action.CreateOrderAction;
import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderRespDTO;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.internal.CreateOrderInO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CreateOrderOperation extends BaseOperation<CreateOrderInO, ParcelOrderRespDTO> {

    @Autowired
    private CreateOrderAction createOrder;

    @Transactional
    @Override
    public ParcelOrderRespDTO execute(CreateOrderInO req) {
        ParcelOrder order = createOrder.execute(req, getCtx());
        return respOrderMapper.map(parcelOrderRepository.save(order));
    }
}
