package com.vshtd.parceldelivery.logistic.operation;

public interface Operation<REQ, RESP> {

    RESP execute(REQ req);

    RESP process(REQ req);

}
