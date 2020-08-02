package com.browarna.railwaycarsserving.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryOfWagon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryOfWagonId;
    private Date startDate;
    private Date endDate;
    private double cargoWeight;
    private boolean loadUnloadWork;
    private double shuntingWorks;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "wagon_id")
    private Wagon wagon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
}
