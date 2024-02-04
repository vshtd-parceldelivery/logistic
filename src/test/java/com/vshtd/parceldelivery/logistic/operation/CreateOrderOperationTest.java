package com.vshtd.parceldelivery.logistic.operation;

import com.vshtd.parceldelivery.logistic.action.CreateOrderAction;
import com.vshtd.parceldelivery.logistic.action.TrackOrderAction;
import com.vshtd.parceldelivery.logistic.mapper.CreateOrderMapper;
import com.vshtd.parceldelivery.logistic.mapper.RespOrderMapper;
import com.vshtd.parceldelivery.logistic.model.ctx.Ctx;
import com.vshtd.parceldelivery.logistic.model.dto.ParcelOrderDTO;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.internal.CreateOrderInO;
import com.vshtd.parceldelivery.logistic.repository.ParcelOrderRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CreateOrderOperationTest extends CommonTest {

    @Mock
    private CreateOrderAction createOrderAction;

    @Mock
    private CreateOrderMapper createOrderMapper;

    @Mock
    private ParcelOrderRepository parcelOrderRepository;

    @Mock
    private RespOrderMapper respOrderMapper;

    @Mock
    private TrackOrderAction trackOrder;

    @InjectMocks
    private CreateOrderOperation createOrder;

    @Test
    public void createOrderTest() {
        ParcelOrder order = justCreatedOrder();
        when(createOrderAction.execute(createOrderReq(), Ctx.builder().build())).thenReturn(order);
        when(createOrderMapper.map(createOrderReq().getParcelOrder())).thenReturn(order);
        when(parcelOrderRepository.save(any(ParcelOrder.class))).thenReturn(order);
        final ArgumentCaptor<ParcelOrder> captorOrder = ArgumentCaptor.forClass(ParcelOrder.class);
        createOrder.process(createOrderReq());
        verify(parcelOrderRepository).save(captorOrder.capture());
        verify(parcelOrderRepository, times(1)).save(captorOrder.capture());
        verify(trackOrder, times(0)).execute(any(Pair.class), any(Ctx.class));
        verify(respOrderMapper).map(captorOrder.capture());
        verify(respOrderMapper, times(1)).map(captorOrder.capture());
    }

    private CreateOrderInO createOrderReq() {
        return CreateOrderInO.builder()
                .name(author)
                .parcelOrder(ParcelOrderDTO.builder()
                        .description(description)
                        .destination(destination)
                        .type(type)
                        .build())
                .build();
    }
}
