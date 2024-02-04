package com.vshtd.parceldelivery.logistic.model.entity;

public enum ParcelOrderStatus {

    CREATED, ORDERED, APPROVED, ASSIGNED, PROCESSED, ON_ROUTE, DELIVERED, CANCELLED;


    public boolean anyOf(ParcelOrderStatus... statuses) {
        for (ParcelOrderStatus status : statuses) {
            if (this == status) {
                return true;
            }
        }
        return false;
    }

    public boolean notAnyOf(ParcelOrderStatus... statuses) {
        for (ParcelOrderStatus status : statuses) {
            if (this == status) {
                return false;
            }
        }
        return true;
    }
}
