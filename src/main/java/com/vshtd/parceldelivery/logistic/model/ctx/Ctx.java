package com.vshtd.parceldelivery.logistic.model.ctx;

import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
public class Ctx {

    public static final String PARCEL_ORDER = "parcelOrder";

    @Builder.Default
    private Map<Object, Object> attrs = new HashMap<>();

    public void set(Object key, Object value) {
        attrs.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Object key) {
        return (T) attrs.get(key);
    }

    public ParcelOrder getOrder() {
        return get(PARCEL_ORDER);
    }

    public ParcelOrder setOrder(ParcelOrder order) {
        set(PARCEL_ORDER, order);
        return order;
    }
}
