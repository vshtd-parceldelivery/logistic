package com.vshtd.parceldelivery.logistic.service.order;

import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderDTO;
import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderRespDTO;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrderStatus;
import com.vshtd.parceldelivery.logistic.model.internal.*;
import com.vshtd.parceldelivery.logistic.operation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParcelOrderServiceImpl implements ParcelOrderService {

    @Autowired
    private CreateOrderOperation createOrder;

    @Autowired
    private GetOrderOperation getOrder;

    @Autowired
    private GetUserOrdersOperation getUserOrders;

    @Autowired
    private GetCourierOrdersOperation getCourierOrders;

    @Autowired
    private UpdateOrderByUserOperation updateOrderByUser;

    @Autowired
    private CancelOrderOperation cancelOrder;

    @Autowired
    private ChangeOrderStatusOperation changeOrderStatus;

    @Autowired
    private GetAllOrdersOperation getAllOrders;

    @Autowired
    private AssignOrderOperation assignOrder;

    @Autowired
    private ChangeOrderStatusByCourierOperation changeOrderStatusByCourier;

    @Autowired
    private TrackOrderOperation trackOrder;

    @Override
    public ParcelOrderRespDTO createOrder(ParcelOrderDTO parcelOrder, String username) {
        return createOrder.process(CreateOrderInO.builder()
                .parcelOrder(parcelOrder)
                .name(username)
                .build());
    }

    @Override
    public ParcelOrderRespDTO getOrder(UUID uuid) {
        return getOrder.process(GetOrderInO.builder().
                orderUuid(uuid)
                .build());
    }

    @Override
    public Page<ParcelOrderRespDTO> getUserOrders(String username, Pageable pageable) {
        return getUserOrders.process(GetUserOrdersInO.builder()
                .username(username)
                .pageable(pageable)
                .build());
    }

    @Override
    public Page<ParcelOrderRespDTO> getCourierOrders(String courierName, Pageable pageable) {
        return getCourierOrders.process(GetCourierOrdersIn.builder()
                .courierName(courierName)
                .pageable(pageable)
                .build());
    }

    @Override
    public ParcelOrderRespDTO updateOrderDestinationByUser(UUID orderUuid, String destination, String username) {
        return updateOrderByUser.process(UpdateOrderByUserInO.builder()
                .orderUuid(orderUuid)
                .destination(destination)
                .username(username)
                .build());
    }

    @Override
    public ParcelOrderRespDTO cancelOrder(UUID orderUuid, String username) {
        return cancelOrder.process(CancelOrderInO.builder()
                .orderUuid(orderUuid)
                .username(username)
                .build());
    }

    @Override
    public ParcelOrderRespDTO changeOrderStatusByCourier(UUID orderUuid, ParcelOrderStatus status) {
        return changeOrderStatus.process(ChangeOrderStatusInO.builder()
                .orderUuid(orderUuid)
                .status(status)
                .build());
    }

    @Override
    public Page<ParcelOrderRespDTO> getAllOrders(Pageable pageable) {
        return getAllOrders.process(pageable);
    }

    @Override
    public ParcelOrderRespDTO assignOrder(String courierName, UUID orderUuid) {
        return assignOrder.process(AssignOrderInO.builder()
                .courierName(courierName)
                .orderUuid(orderUuid)
                .build());
    }

    @Override
    public ParcelOrderRespDTO changeOrderStatusByCourier(UUID orderUuid, ParcelOrderStatus status, String courierName) {
        return changeOrderStatusByCourier.process(ChangeOrderStatusByCourierInO.builder()
                .courierName(courierName)
                .orderUuid(orderUuid)
                .status(status)
                .build());
    }

    @Override
    public ParcelOrderRespDTO track(UUID orderUuid, String username) {
        return trackOrder.process(TrackOrderInO.builder()
                .orderUuid(orderUuid)
                .username(username)
                .build());
    }
}
