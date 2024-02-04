package com.vshtd.parceldelivery.logistic.controller;

import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderRespDTO;
import com.vshtd.parceldelivery.logistic.service.order.ParcelOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@PreAuthorize("hasAnyAuthority('ROLE_USER')")
@RequestMapping("/logistic/parcel-order/user")
@Tag(name = "User", description = "user order API")
public class UserParcelOrderController {

    @Autowired
    private ParcelOrderService parcelOrderService;

    @GetMapping
    @Operation(summary = "get user own orders")
    public Page<ParcelOrderRespDTO> getUserOrders(Principal principal,
                                                  Pageable pageable) {
        return parcelOrderService.getUserOrders(principal.getName(), pageable);
    }

    @PatchMapping
    @Operation(summary = "update order destination by user")
    public ParcelOrderRespDTO updateOrderDestination(@RequestParam(name = "orderUuid") UUID orderUuid,
                                                     @RequestParam(name = "destination") String destination,
                                                     Principal principal) {
        return parcelOrderService.updateOrderDestinationByUser(orderUuid, destination, principal.getName());
    }

    @DeleteMapping
    @Operation(summary = "cancel order")
    public ParcelOrderRespDTO cancelOrder(@RequestParam(name = "orderUuid") UUID orderUuid,
                                          Principal principal) {
        return parcelOrderService.cancelOrder(orderUuid, principal.getName());
    }
}
