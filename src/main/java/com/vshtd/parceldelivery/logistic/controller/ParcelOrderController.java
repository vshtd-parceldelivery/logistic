package com.vshtd.parceldelivery.logistic.controller;

import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderDTO;
import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderRespDTO;
import com.vshtd.parceldelivery.logistic.service.order.ParcelOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/logistic/parcel-order")
@Tag(name = "Order", description = "common order API")
public class ParcelOrderController {

    @Autowired
    private ParcelOrderService parcelOrderService;

    @PostMapping
    @Operation(summary = "create order")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
    public ParcelOrderRespDTO createOrder(@RequestBody ParcelOrderDTO parcelOrder, Principal principal) {
        return parcelOrderService.createOrder(parcelOrder, principal.getName());
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "get order by uuid")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_COURIER')")
    public ParcelOrderRespDTO getOrder(@PathVariable(name = "uuid") UUID uuid) {
        return parcelOrderService.getOrder(uuid);
    }
}
