package com.vshtd.parceldelivery.logistic.operation;

import com.vshtd.parceldelivery.logistic.action.GetOrderByUserAndUuidAction;
import com.vshtd.parceldelivery.logistic.action.TrackOrderAction;
import com.vshtd.parceldelivery.logistic.mapper.RespOrderMapper;
import com.vshtd.parceldelivery.logistic.model.ctx.Ctx;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.internal.CancelOrderInO;
import com.vshtd.parceldelivery.logistic.model.internal.IBothUserOrder;
import com.vshtd.parceldelivery.logistic.repository.ParcelOrderRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.vshtd.parceldelivery.logistic.model.entity.ParcelOrderStatus.CANCELLED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CancelOrderOperationTest extends CommonTest {

    @InjectMocks
    private CancelOrderOperation cancelOrder;

    @Mock
    private GetOrderByUserAndUuidAction getOrderByUserAndUuid;

    @Mock
    private ParcelOrderRepository parcelOrderRepository;

    @Mock
    private RespOrderMapper respOrderMapper;

    @Mock
    private TrackOrderAction trackOrder;

    @Test
    public void cancelOrderTest() {
        ParcelOrder order = justCreatedOrder();
        when(getOrderByUserAndUuid.execute(any(IBothUserOrder.class), any(Ctx.class))).thenReturn(order);
        when(parcelOrderRepository.save(any(ParcelOrder.class))).thenReturn(order);
        final ArgumentCaptor<ParcelOrder> captorOrder = ArgumentCaptor.forClass(ParcelOrder.class);
        cancelOrder.process(cancelOrderReq());
        verify(parcelOrderRepository).save(captorOrder.capture());
        verify(parcelOrderRepository, times(1)).save(captorOrder.capture());
        verify(respOrderMapper).map(captorOrder.capture());
        verify(respOrderMapper, times(1)).map(captorOrder.capture());
        verify(trackOrder, times(1)).execute(any(Pair.class), any(Ctx.class));
        assertEquals(captorOrder.getValue().getStatus(), CANCELLED);
    }

    @Test
    public void cantCancelOrderTest() {
        ParcelOrder order = alreadyProcessedOrder();
        when(getOrderByUserAndUuid.execute(any(IBothUserOrder.class), any(Ctx.class))).thenReturn(order);
        assertThrows(RuntimeException.class,
                () -> {
                    cancelOrder.process(cancelOrderReq());
                });
    }

    @Test
    public void noOrderTestTest() {
        when(getOrderByUserAndUuid.execute(any(IBothUserOrder.class), any(Ctx.class))).thenReturn(null);
        assertThrows(RuntimeException.class,
                () -> {
                    cancelOrder.process(cancelOrderReq());
                });
    }

    private CancelOrderInO cancelOrderReq() {
        return CancelOrderInO.builder()
                .orderUuid(uuid)
                .username(author)
                .build();
    }
}