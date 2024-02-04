package com.vshtd.parceldelivery.logistic.mapper;

import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.entity.Watcher;
import com.vshtd.parceldelivery.logistic.model.mqo.ParcelOrderTrackMQO;
import com.vshtd.parceldelivery.logistic.model.mqo.TrackEventParam;
import com.vshtd.parceldelivery.logistic.model.mqo.TrackEventType;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TrackEventMapper implements Mapper<Pair<ParcelOrder, TrackEventType>, ParcelOrderTrackMQO> {

    @Override
    public ParcelOrderTrackMQO map(Pair<ParcelOrder, TrackEventType> eventPair) {
        if (Objects.isNull(eventPair)) {
            return ParcelOrderTrackMQO.builder().build();
        }
        return ParcelOrderTrackMQO.builder()
                .eventType(eventPair.getRight())
                .eventParam(param(eventPair))
                .orderUuid(eventPair.getLeft().getUuid())
                .recipients(recipients(eventPair.getLeft()))
                .build();
    }

    private Map<TrackEventParam, String> param(Pair<ParcelOrder, TrackEventType> eventPair) {
        return switch (eventPair.getRight()) {
            case STATUS_CHANGED ->
                    Map.of(TrackEventParam.STATUS, eventPair.getLeft().getStatus().toString());
            case COURIER_ASSIGNED ->
                    Map.of(TrackEventParam.COURIER_NAME, eventPair.getLeft().getCourier());
            case DESTINATION_CHANGED ->
                    Map.of(TrackEventParam.DESTINATION, eventPair.getLeft().getDestination());
            case DELIVERY_EXPECTED_DATE ->
                    Map.of(TrackEventParam.DELIVERY_DATE, eventPair.getLeft().getExpectedDeliveryDate().toString());
        };
    }

    private Set<String> recipients(ParcelOrder order) {
        return order.getWatchers().stream()
                .map(Watcher::getWatcherName)
                .collect(Collectors.toSet());
    }
}
