package com.vshtd.parceldelivery.logistic.controller;

import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderRespDTO;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrderStatus;
import com.vshtd.parceldelivery.logistic.service.order.ParcelOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
@RequestMapping("/logistic/parcel-order")
@Tag(name = "Admin", description = "admin order API")
public class AdminParcelOrderController {

    @Autowired
    private ParcelOrderService parcelOrderService;

    @GetMapping("/user/{username}")
    @Operation(summary = "get user orders")
    public Page<ParcelOrderRespDTO> getUserOrders(@PathVariable(name = "username") String username,
                                                  @ParameterObject Pageable pageable) {
        return parcelOrderService.getUserOrders(username, pageable);
    }

    @GetMapping
    @Operation(summary = "get all orders")
    public Page<ParcelOrderRespDTO> getAllOrders(@ParameterObject Pageable pageable) {
        return parcelOrderService.getAllOrders(pageable);
    }

    @GetMapping("/courier/{courierName}")
    @Operation(summary = "get courier orders")
    public Page<ParcelOrderRespDTO> getCourierOrders(@PathVariable(name = "courierName") String courierName,
                                                     @ParameterObject Pageable pageable) {
        return parcelOrderService.getCourierOrders(courierName, pageable);
    }

    @DeleteMapping("/user/{username}")
    @Operation(summary = "cancel order")
    public ParcelOrderRespDTO cancelOrder(@PathVariable(name = "username") String username,
                                          @RequestParam(name = "orderUuid") UUID orderUuid) {
        return parcelOrderService.cancelOrder(orderUuid, username);
    }

    @PatchMapping
    @Operation(summary = "change order status")
    public ParcelOrderRespDTO changeOrderStatus(@RequestParam(name = "orderUuid") UUID orderUuid,
                                                @RequestParam(name = "status") ParcelOrderStatus status) {
        return parcelOrderService.changeOrderStatusByCourier(orderUuid, status);
    }

    @PatchMapping("/courier/{courierName}/assign")
    @Operation(summary = "assign order to courier")
    public ParcelOrderRespDTO assignOrder(@PathVariable(name = "courierName") String courierName,
                                          @RequestParam(name = "orderUuid") UUID orderUuid) {
        return parcelOrderService.assignOrder(courierName, orderUuid);
    }

    @PatchMapping("/{uuid}/track")
    @Operation(summary = "track order")
    public ParcelOrderRespDTO trackOrder(@PathVariable(name = "uuid") UUID uuid,
                                         Principal principal) {
        return parcelOrderService.track(uuid, principal.getName());
    }
}
