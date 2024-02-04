package com.vshtd.parceldelivery.logistic.operation;

import com.vshtd.parceldelivery.logistic.action.GetOrderAction;
import com.vshtd.parceldelivery.logistic.action.GetOrderByCourierAndUuidAction;
import com.vshtd.parceldelivery.logistic.action.TrackOrderAction;
import com.vshtd.parceldelivery.logistic.mapper.RespOrderMapper;
import com.vshtd.parceldelivery.logistic.model.ctx.Ctx;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.internal.AssignOrderInO;
import com.vshtd.parceldelivery.logistic.model.internal.IBothCourierOrder;
import com.vshtd.parceldelivery.logistic.model.internal.IOrder;
import com.vshtd.parceldelivery.logistic.repository.ParcelOrderRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AssignOrderOperationTest extends CommonTest {

    @Mock
    private GetOrderByCourierAndUuidAction getCourierOrder;

    @Mock
    private GetOrderAction getOrder;

    @Mock
    private ParcelOrderRepository parcelOrderRepository;

    @Mock
    private RespOrderMapper respOrderMapper;

    @Mock
    private TrackOrderAction trackOrder;

    @InjectMocks
    private AssignOrderOperation assignOrder;

    @Test
    public void assignOrderTest() {
        ParcelOrder order = justCreatedOrder();
        when(getOrder.execute(any(IOrder.class), any(Ctx.class))).thenReturn(order);
        when(getCourierOrder.execute(any(IBothCourierOrder.class), any(Ctx.class))).thenReturn(null);
        when(parcelOrderRepository.save(any(ParcelOrder.class))).thenReturn(order);
        final ArgumentCaptor<ParcelOrder> captorOrder = ArgumentCaptor.forClass(ParcelOrder.class);
        assignOrder.process(assignOrderReq());
        verify(parcelOrderRepository).save(captorOrder.capture());
        verify(parcelOrderRepository, times(1)).save(captorOrder.capture());
        verify(respOrderMapper).map(captorOrder.capture());
        verify(respOrderMapper, times(1)).map(captorOrder.capture());
        verify(trackOrder, times(1)).execute(any(Pair.class), any(Ctx.class));
        assertEquals(captorOrder.getValue().getCourier(), courier);
        assertEquals(captorOrder.getValue().getWatchers().size(), 1);
        assertEquals(captorOrder.getValue().getWatchers().iterator().next().getWatcherName(), courier);
    }

    @Test
    public void assignedOrderTest() {
        ParcelOrder order = alreadyAssignedOrder();
        when(getOrder.execute(any(IOrder.class), any(Ctx.class))).thenReturn(null);
        when(getCourierOrder.execute(any(IBothCourierOrder.class), any(Ctx.class))).thenReturn(order);
        when(parcelOrderRepository.save(any(ParcelOrder.class))).thenReturn(order);
        final ArgumentCaptor<ParcelOrder> captorOrder = ArgumentCaptor.forClass(ParcelOrder.class);
        assignOrder.getCtx().setOrder(order);
        assignOrder.process(assignOrderReq());
        verify(parcelOrderRepository).save(captorOrder.capture());
        verify(parcelOrderRepository, times(1)).save(captorOrder.capture());
        verify(respOrderMapper).map(captorOrder.capture());
        verify(respOrderMapper, times(1)).map(captorOrder.capture());
        verify(getOrder, times(0)).execute(any(IOrder.class), any(Ctx.class));
        verify(trackOrder, times(1)).execute(any(Pair.class), any(Ctx.class));
        assertFalse(captorOrder.getValue().isNotify());
        assertEquals(captorOrder.getValue().getCourier(), courier);
        assertEquals(captorOrder.getValue().getWatchers().size(), 1);
        assertEquals(captorOrder.getValue().getWatchers().iterator().next().getWatcherName(), courier);
    }

    @Test
    public void noOrderTest() {
        when(getOrder.execute(any(IOrder.class), any(Ctx.class))).thenReturn(null);
        when(getCourierOrder.execute(any(IBothCourierOrder.class), any(Ctx.class))).thenReturn(null);
        assertThrows(RuntimeException.class,
                () -> {
                    assignOrder.process(assignOrderReq());
                }, "Such order does not exist " + uuid);
    }

    private AssignOrderInO assignOrderReq() {
        return AssignOrderInO.builder()
                .courierName(courier)
                .orderUuid(uuid)
                .build();
    }
}
