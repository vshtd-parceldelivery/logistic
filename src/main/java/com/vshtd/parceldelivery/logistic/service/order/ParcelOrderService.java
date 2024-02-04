package com.vshtd.parceldelivery.logistic.service.order;

import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderDTO;
import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderRespDTO;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ParcelOrderService {

    ParcelOrderRespDTO createOrder(ParcelOrderDTO parcelOrder, String name);

    ParcelOrderRespDTO getOrder(UUID uuid);

    Page<ParcelOrderRespDTO> getUserOrders(String username, Pageable pageable);

    Page<ParcelOrderRespDTO> getCourierOrders(String courierName, Pageable pageable);

    ParcelOrderRespDTO updateOrderDestinationByUser(UUID orderUuid, String destination, String name);

    ParcelOrderRespDTO cancelOrder(UUID orderUuid, String name);

    ParcelOrderRespDTO changeOrderStatusByCourier(UUID orderUuid, ParcelOrderStatus status);

    Page<ParcelOrderRespDTO> getAllOrders(Pageable pageable);

    ParcelOrderRespDTO assignOrder(String courierName, UUID orderUuid);

    ParcelOrderRespDTO changeOrderStatusByCourier(UUID orderUuid, ParcelOrderStatus status, String name);

    ParcelOrderRespDTO track(UUID uuid, String name);
}
