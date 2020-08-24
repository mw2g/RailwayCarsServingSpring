package com.browarna.railwaycarsserving.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String customerName;
    private String customerFullName;
    private Instant created;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany( mappedBy = "customer",
            fetch = FetchType.EAGER,
            cascade = { CascadeType.ALL}
    )
    private List<Signer> signerList;

    public void add(Signer tempSignerList) {
        if (signerList == null) {
            signerList = new ArrayList<>();
        }
        signerList.add(tempSignerList);
        tempSignerList.setCustomer(this);
    }
}
