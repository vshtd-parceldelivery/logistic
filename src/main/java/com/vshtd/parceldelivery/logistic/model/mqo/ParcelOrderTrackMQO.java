package com.vshtd.parceldelivery.logistic.model.mqo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ParcelOrderTrackMQO implements Serializable {

    private UUID orderUuid;
    private Set<String> recipients;
    private TrackEventType eventType;
    private Map<TrackEventParam, String> eventParam;
}
