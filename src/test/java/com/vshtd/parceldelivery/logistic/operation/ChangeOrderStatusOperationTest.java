package com.vshtd.parceldelivery.logistic.operation;

import com.vshtd.parceldelivery.logistic.action.GetOrderAction;
import com.vshtd.parceldelivery.logistic.action.TrackOrderAction;
import com.vshtd.parceldelivery.logistic.mapper.RespOrderMapper;
import com.vshtd.parceldelivery.logistic.model.ctx.Ctx;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.internal.ChangeOrderStatusInO;
import com.vshtd.parceldelivery.logistic.model.internal.IOrder;
import com.vshtd.parceldelivery.logistic.repository.ParcelOrderRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.vshtd.parceldelivery.logistic.model.entity.ParcelOrderStatus.ON_ROUTE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ChangeOrderStatusOperationTest extends CommonTest {

    @Mock
    GetOrderAction getOrder;

    @Mock
    private ParcelOrderRepository parcelOrderRepository;

    @Mock
    private RespOrderMapper respOrderMapper;

    @Mock
    private TrackOrderAction trackOrder;

    @InjectMocks
    private ChangeOrderStatusOperation changeOrderStatus;

    @Test
    public void changeOrderStatusTest() {
        ParcelOrder order = alreadyProcessedOrder();
        when(getOrder.execute(any(IOrder.class), any(Ctx.class))).thenReturn(order);
        when(parcelOrderRepository.save(any(ParcelOrder.class))).thenReturn(order);
        final ArgumentCaptor<ParcelOrder> captorOrder = ArgumentCaptor.forClass(ParcelOrder.class);
        changeOrderStatus.process(changeOrderReq());
        verify(parcelOrderRepository).save(captorOrder.capture());
        verify(parcelOrderRepository, times(1)).save(captorOrder.capture());
        verify(respOrderMapper).map(captorOrder.capture());
        verify(respOrderMapper, times(1)).map(captorOrder.capture());
        verify(trackOrder, times(1)).execute(any(Pair.class), any(Ctx.class));
        assertEquals(captorOrder.getValue().getStatus(), ON_ROUTE);
    }

    @Test
    public void noOrderTest() {
        when(getOrder.execute(any(IOrder.class), any(Ctx.class))).thenReturn(null);
        assertThrows(RuntimeException.class,
                () -> {
                    changeOrderStatus.process(changeOrderReq());
                });
    }

    ChangeOrderStatusInO changeOrderReq() {
        return ChangeOrderStatusInO.builder()
                .status(ON_ROUTE)
                .orderUuid(uuid)
                .build();
    }
}
