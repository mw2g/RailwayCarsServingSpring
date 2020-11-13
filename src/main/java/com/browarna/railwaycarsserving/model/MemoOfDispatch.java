package com.browarna.railwaycarsserving.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MemoOfDispatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memoOfDispatchId;

    private Date created;
    private Date endDate;
    private String comment;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany( mappedBy = "memoOfDispatch",
            fetch = FetchType.LAZY,
            cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<DeliveryOfWagon> deliveryOfWagonList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "statementId")
    private Statement statement;

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
        return "Memo of dispatch{" +
                "id=" + memoOfDispatchId +
                ", createdDate=" + created +
                ", endDate='" + endDate +
                '}';
    }
}
