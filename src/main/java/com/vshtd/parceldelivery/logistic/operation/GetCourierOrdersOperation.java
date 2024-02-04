package com.vshtd.parceldelivery.logistic.operation;

import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderRespDTO;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.internal.GetCourierOrdersIn;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetCourierOrdersOperation extends BaseOperation<GetCourierOrdersIn, Page<ParcelOrderRespDTO>> {

    @Transactional(readOnly = true)
    @Override
    public Page<ParcelOrderRespDTO> execute(GetCourierOrdersIn req) {
        Page<ParcelOrder> orders = parcelOrderRepository.findByCourier(req.getCourierName(), req.getPageable());
        return orders.map(order -> respOrderMapper.map(order));
    }
}
