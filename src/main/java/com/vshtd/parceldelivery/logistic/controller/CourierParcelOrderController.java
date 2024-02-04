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
import java.util.List;
import java.util.UUID;

@RestController
@PreAuthorize("hasAnyAuthority('ROLE_COURIER')")
@RequestMapping("/logistic/parcel-order/courier")
@Tag(name = "Courier", description = "courier order API")
public class CourierParcelOrderController {

    @Autowired
    private ParcelOrderService parcelOrderService;

    @GetMapping
    @Operation(summary = "get courier assigned orders")
    public Page<ParcelOrderRespDTO> getCourierOrders(Principal principal,
                                                     @ParameterObject Pageable pageable) {
        return parcelOrderService.getCourierOrders(principal.getName(), pageable);
    }

    @PatchMapping
    @Operation(summary = "change order status by courier")
    public ParcelOrderRespDTO changeOrderStatus(@RequestParam(name = "orderUuid") UUID orderUuid,
                                                @RequestParam(name = "status") ParcelOrderStatus status,
                                                Principal principal) {
        return parcelOrderService.changeOrderStatusByCourier(orderUuid, status, principal.getName());
    }


}
