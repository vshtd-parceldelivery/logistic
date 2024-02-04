package com.vshtd.parceldelivery.logistic.model.entity;

import com.google.common.collect.Sets;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import static com.vshtd.parceldelivery.logistic.model.entity.ParcelOrderStatus.*;
import static com.vshtd.parceldelivery.logistic.model.entity.ParcelOrderType.LETTER;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parcel_order")
public class ParcelOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "parcel_order_seq", allocationSize = 1)
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "author")
    private String author;

    @Column(name = "description")
    private String description;

    @Column(name = "destination")
    private String destination;

    @Builder.Default
    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private ParcelOrderStatus status = CREATED;

    @Builder.Default
    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    private ParcelOrderType type = LETTER;

    @Column(name = "courier")
    private String courier;

    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate = new Date();

    @Builder.Default
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "exp_delivery_date")
    private Date expectedDeliveryDate;

    @Builder.Default
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Watcher> watchers = Sets.newHashSet();

    @Transient
    @Builder.Default
    private boolean notify = true;

    public void setDestination(String destination) {
        this.destination = destination;
        this.updateDate = new Date();
    }

    public void setStatus(ParcelOrderStatus status) {
        this.status = status;
        this.updateDate = new Date();
    }

    public void setCourier(String name) {
        this.courier = name;
        this.watchers.add(Watcher.builder()
                .watcherName(name)
                .order(this)
                .build());
        this.status = ASSIGNED;
        this.updateDate = new Date();
    }

    public boolean cantUserChangeStatus() {
        return !canUserChangeStatus();
    }

    public boolean cantUserChangeDestination() {
        return cantUserChangeStatus();
    }

    public boolean canUserChangeStatus() {
        return this.status.anyOf(CREATED, ORDERED, APPROVED, ASSIGNED);
    }

    public boolean canCourierChangeStatus() {
        return this.status.anyOf(ASSIGNED, PROCESSED, ON_ROUTE);
    }

    public boolean cantCourierChangeStatus() {
        return !canCourierChangeStatus();
    }

    public void track(String name) {
        this.watchers.add(Watcher.builder()
                .watcherName(name)
                .order(this)
                .build());
        this.updateDate = new Date();
    }

    public void updateDate() {
        this.updateDate = new Date();
    }

    public void doNotNotify() {
        this.notify = false;
    }
}
