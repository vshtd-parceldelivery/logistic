package com.vshtd.parceldelivery.logistic.operation;

import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrderStatus;
import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrderType;
import com.vshtd.parceldelivery.logistic.model.entity.Watcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(SpringExtension.class)
public class CommonTest {

    protected static final String author = "author";
    protected static final String admin = "admin";
    protected static final String courier = "courier";
    protected static final String description = "description";
    protected static final String destination = "destination";
    protected static final ParcelOrderType type = ParcelOrderType.LETTER;
    protected static final UUID uuid = UUID.fromString("ceb438dd-c13c-11ee-9652-d7c1c860b541");

    @BeforeAll
    static void init() {
        MockitoAnnotations.openMocks(CommonTest.class);
    }

    protected ParcelOrder justCreatedOrder() {
        return ParcelOrder.builder()
                .author(author)
                .status(ParcelOrderStatus.CREATED)
                .build();
    }

    protected ParcelOrder alreadyProcessedOrder() {
        return ParcelOrder.builder()
                .author(author)
                .status(ParcelOrderStatus.PROCESSED)
                .build();
    }

    protected ParcelOrder alreadyAssignedOrder() {
        ParcelOrder order = ParcelOrder.builder()
                .author(author)
                .status(ParcelOrderStatus.ASSIGNED)
                .courier(courier)
                .build();
        order.getWatchers().add(Watcher.builder()
                .watcherName(courier)
                .build());
        return order;
    }
}
