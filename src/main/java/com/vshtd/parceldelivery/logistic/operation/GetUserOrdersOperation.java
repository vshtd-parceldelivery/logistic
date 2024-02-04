package com.vshtd.parceldelivery.logistic.operation;

import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderRespDTO;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.internal.GetUserOrdersInO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetUserOrdersOperation extends BaseOperation<GetUserOrdersInO, Page<ParcelOrderRespDTO>> {

    @Transactional(readOnly = true)
    @Override
    public Page<ParcelOrderRespDTO> execute(GetUserOrdersInO req) {
        Page<ParcelOrder> orders = parcelOrderRepository.findByAuthor(req.getUsername(), req.getPageable());
        return orders.map(order -> respOrderMapper.map(order));
    }
}
