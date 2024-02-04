package com.vshtd.parceldelivery.logistic.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "watcher")
public class Watcher {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "watcher_seq", allocationSize = 1)
    private Long id;

    @Column(name = "watcher_name")
    private String watcherName;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private ParcelOrder order;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Watcher watcher = (Watcher) o;

        return new EqualsBuilder()
                .append(watcherName, watcher.watcherName)
                .append(order, watcher.order)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(watcherName)
                .append(order)
                .toHashCode();
    }
}
