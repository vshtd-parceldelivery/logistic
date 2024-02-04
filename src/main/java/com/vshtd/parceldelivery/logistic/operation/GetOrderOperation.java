package com.vshtd.parceldelivery.logistic.operation;

import com.vshtd.parceldelivery.logistic.action.GetOrderAction;
import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderRespDTO;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.internal.GetOrderInO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GetOrderOperation extends BaseOperation<GetOrderInO, ParcelOrderRespDTO> {

    @Autowired
    private GetOrderAction getOrder;

    @Transactional
    @Override
    public ParcelOrderRespDTO execute(GetOrderInO req) {
        ParcelOrder order = getOrder.execute(req, getCtx());
        checkNoOrderException(order, req);
        return respOrderMapper.map(order);
    }
}
