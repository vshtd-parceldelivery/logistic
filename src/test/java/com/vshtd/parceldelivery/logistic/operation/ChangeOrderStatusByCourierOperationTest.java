package com.vshtd.parceldelivery.logistic.operation;

import com.vshtd.parceldelivery.logistic.action.GetOrderByCourierAndUuidAction;
import com.vshtd.parceldelivery.logistic.action.TrackOrderAction;
import com.vshtd.parceldelivery.logistic.mapper.RespOrderMapper;
import com.vshtd.parceldelivery.logistic.model.ctx.Ctx;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.internal.ChangeOrderStatusByCourierInO;
import com.vshtd.parceldelivery.logistic.model.internal.IBothCourierOrder;
import com.vshtd.parceldelivery.logistic.repository.ParcelOrderRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.vshtd.parceldelivery.logistic.model.entity.ParcelOrderStatus.DELIVERED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ChangeOrderStatusByCourierOperationTest extends CommonTest {

    @Mock
    private GetOrderByCourierAndUuidAction getOrder;

    @Mock
    private ParcelOrderRepository parcelOrderRepository;

    @Mock
    private RespOrderMapper respOrderMapper;

    @Mock
    private TrackOrderAction trackOrder;

    @InjectMocks
    private ChangeOrderStatusByCourierOperation changeOrderStatusByCourier;

    @Test
    public void changeOrderStatusTest() {
        ParcelOrder order = alreadyProcessedOrder();
        when(getOrder.execute(any(IBothCourierOrder.class), any(Ctx.class))).thenReturn(order);
        when(parcelOrderRepository.save(any(ParcelOrder.class))).thenReturn(order);
        final ArgumentCaptor<ParcelOrder> captorOrder = ArgumentCaptor.forClass(ParcelOrder.class);
        changeOrderStatusByCourier.process(changeOrderReq());
        verify(parcelOrderRepository).save(captorOrder.capture());
        verify(parcelOrderRepository, times(1)).save(captorOrder.capture());
        verify(respOrderMapper).map(captorOrder.capture());
        verify(respOrderMapper, times(1)).map(captorOrder.capture());
        verify(trackOrder, times(1)).execute(any(Pair.class), any(Ctx.class));
        assertEquals(captorOrder.getValue().getStatus(), DELIVERED);
    }

    @Test
    public void cantChangeOrderStatusTest() {
        ParcelOrder order = justCreatedOrder();
        when(getOrder.execute(any(IBothCourierOrder.class), any(Ctx.class))).thenReturn(order);
        assertThrows(RuntimeException.class,
                () -> {
                    changeOrderStatusByCourier.process(changeOrderReq());
                });
    }

    @Test
    public void noOrderTestTest() {
        when(getOrder.execute(any(IBothCourierOrder.class), any(Ctx.class))).thenReturn(null);
        assertThrows(RuntimeException.class,
                () -> {
                    changeOrderStatusByCourier.process(changeOrderReq());
                });
    }

    ChangeOrderStatusByCourierInO changeOrderReq() {
        return ChangeOrderStatusByCourierInO.builder()
                .status(DELIVERED)
                .orderUuid(uuid)
                .courierName(courier)
                .build();
    }
}