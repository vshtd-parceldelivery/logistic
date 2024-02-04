package com.vshtd.parceldelivery.logistic.operation;

import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderRespDTO;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class GetAllOrdersOperation extends BaseOperation<Pageable, Page<ParcelOrderRespDTO>> {

    @Transactional(readOnly = true)
    @Override
    public Page<ParcelOrderRespDTO> execute(Pageable pageable) {
        Page<ParcelOrder> orders = parcelOrderRepository.findAll(pageable);
        return orders.map(order -> respOrderMapper.map(order));
    }
}
