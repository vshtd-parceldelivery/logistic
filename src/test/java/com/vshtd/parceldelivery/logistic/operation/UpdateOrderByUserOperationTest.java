package com.vshtd.parceldelivery.logistic.operation;

import com.vshtd.parceldelivery.logistic.action.GetOrderByUserAndUuidAction;
import com.vshtd.parceldelivery.logistic.action.TrackOrderAction;
import com.vshtd.parceldelivery.logistic.mapper.RespOrderMapper;
import com.vshtd.parceldelivery.logistic.model.ctx.Ctx;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.internal.IBothUserOrder;
import com.vshtd.parceldelivery.logistic.model.internal.UpdateOrderByUserInO;
import com.vshtd.parceldelivery.logistic.repository.ParcelOrderRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UpdateOrderByUserOperationTest extends CommonTest {

    @Mock
    private GetOrderByUserAndUuidAction getOrder;

    @Mock
    private ParcelOrderRepository parcelOrderRepository;

    @Mock
    private RespOrderMapper respOrderMapper;

    @Mock
    private TrackOrderAction trackOrder;

    @InjectMocks
    private UpdateOrderByUserOperation updateOrderByUser;

    @Test
    public void updateOrderByUserTest() {
        ParcelOrder order = justCreatedOrder();
        when(getOrder.execute(any(IBothUserOrder.class), any(Ctx.class))).thenReturn(order);
        when(parcelOrderRepository.save(any(ParcelOrder.class))).thenReturn(order);
        final ArgumentCaptor<ParcelOrder> captorOrder = ArgumentCaptor.forClass(ParcelOrder.class);
        updateOrderByUser.process(updateOrderByUserReq());
        verify(parcelOrderRepository).save(captorOrder.capture());
        verify(parcelOrderRepository, times(1)).save(captorOrder.capture());
        verify(respOrderMapper).map(captorOrder.capture());
        verify(respOrderMapper, times(1)).map(captorOrder.capture());
        verify(trackOrder, times(1)).execute(any(Pair.class), any(Ctx.class));
        assertEquals(captorOrder.getValue().getDestination(), destination);
    }

    @Test
    public void noOrderTest() {
        when(getOrder.execute(any(IBothUserOrder.class), any(Ctx.class))).thenReturn(null);
        assertThrows(RuntimeException.class,
                () -> {
                    updateOrderByUser.process(updateOrderByUserReq());
                });
    }

    @Test
    public void cantChangeOrderDestinationTest() {
        ParcelOrder order = alreadyProcessedOrder();
        when(getOrder.execute(any(IBothUserOrder.class), any(Ctx.class))).thenReturn(order);
        assertThrows(RuntimeException.class,
                () -> {
                    updateOrderByUser.process(updateOrderByUserReq());
                }, "You can no longer change destination of order!");
    }

    private UpdateOrderByUserInO updateOrderByUserReq() {
        return UpdateOrderByUserInO.builder()
                .username(author)
                .destination(destination)
                .orderUuid(uuid)
                .build();
    }
}
