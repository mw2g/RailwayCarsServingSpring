package com.browarna.railwaycarsserving.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MemoOfDelivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memoOfDeliveryId;

    private Date created;
    private Date startDate;
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany( mappedBy = "memoOfDelivery",
            fetch = FetchType.LAZY,
            cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH }
    )
    private List<DeliveryOfWagon> deliveryOfWagonList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "operation_id")
    private CargoOperation cargoOperation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "signer_id")
    private Signer signer;

    @Override
    public String toString() {
        return "Memo of delivery{" +
                "id=" + memoOfDeliveryId +
                ", createdDate=" + created +
                ", startDate='" + startDate +
                '}';
    }
}
