package com.vshtd.parceldelivery.logistic.action;

import com.vshtd.parceldelivery.logistic.model.ctx.Ctx;

public interface Action<REQ, RESP> {

    RESP execute(REQ req, Ctx ctx);

}
