package com.browarna.railwaycarsserving.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryOfWagon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryId;
    private Instant created;
    private Date startDate;
    private Date endDate;
    private double cargoWeight;
    private boolean loadUnloadWork;
    private double shuntingWorks;

    @ManyToOne( fetch = FetchType.EAGER,
            cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JoinColumn(name = "memoOfDeliveryId")
    private MemoOfDelivery memoOfDelivery;

    @ManyToOne( fetch = FetchType.EAGER,
            cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    @JoinColumn(name = "memoOfDispatchId")
    private MemoOfDispatch memoOfDispatch;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "operation_id")
    private CargoOperation cargoOperation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cargo_type_id")
    private CargoType cargoType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wagon_id")
    private Wagon wagon;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private WagonType wagonType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @Override
    public String toString() {
        return "Delivery of wagon{" +
                "id=" + deliveryId +
                ", createdDate=" + created +
                ", startDate='" + startDate +
                '}';
    }
}
