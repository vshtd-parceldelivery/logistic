package com.vshtd.parceldelivery.logistic.operation;

import com.vshtd.parceldelivery.logistic.mapper.RespOrderMapper;
import com.vshtd.parceldelivery.logistic.model.ctx.Ctx;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.internal.IOrder;
import com.vshtd.parceldelivery.logistic.repository.ParcelOrderRepository;
import lombok.Builder;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Getter
public abstract class BaseOperation<REQ, RESP> implements Operation<REQ, RESP> {

    @Builder.Default
    private Ctx ctx = Ctx.builder().build();

    @Autowired
    protected ParcelOrderRepository parcelOrderRepository;

    @Autowired
    protected RespOrderMapper respOrderMapper;

    protected void preOperation(REQ req) {
    }

    protected void postOperation(REQ req) {
    }

    @Override
    public RESP process(REQ req) {
        preOperation(req);
        RESP resp = execute(req);
        postOperation(req);
        return resp;
    }

    protected void checkNoOrderException(ParcelOrder order, IOrder req) {
        if (Objects.isNull(order)) {
            throw new RuntimeException("Such order does not exist " + req.getOrderUuid());
        }
    }
}
