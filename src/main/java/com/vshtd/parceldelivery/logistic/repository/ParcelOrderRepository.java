package com.vshtd.parceldelivery.logistic.repository;

import com.vshtd.parceldelivery.logistic.model.entity.ParcelOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ParcelOrderRepository extends JpaRepository<ParcelOrder, Long> {

    @Query(value = "select po from ParcelOrder po where po.uuid = :uuid")
    ParcelOrder findByUuid(@Param(value = "uuid") UUID uuid);

    @Query(value = "select po from ParcelOrder po where po.author in (:author)")
    Page<ParcelOrder> findByAuthor(@Param(value = "author") String author,
                                   Pageable pageable);

    @Query(value = "select po from ParcelOrder po where po.courier = :courierName")
    Page<ParcelOrder> findByCourier(@Param(value = "courierName") String courierName,
                                    Pageable pageable);

    @Query(value = "select po from ParcelOrder po where po.author = :author and po.uuid = :uuid")
    ParcelOrder findByAuthorAndUuid(@Param(value = "author") String author,
                                    @Param(value = "uuid") UUID uuid);

    @Query(value = "select po from ParcelOrder po where po.courier = :courierName and po.uuid = :uuid")
    ParcelOrder findByCourierAndUuid(@Param(value = "courierName") String courierName,
                                     @Param(value = "uuid") UUID uuid);
}
