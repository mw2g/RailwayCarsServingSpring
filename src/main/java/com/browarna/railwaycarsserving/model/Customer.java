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
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String customerName;
    private String customerFullName;
    private Date created;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany( mappedBy = "customer",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<Signer> signerList;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + customerId +
                ", name=" + customerName +
                ", fullName='" + customerFullName +
                '}';
    }

//    public void add(Signer tempSignerList) {
//        if (signerList == null) {
//            signerList = new ArrayList<>();
//        }
//        signerList.add(tempSignerList);
//        tempSignerList.setCustomer(this);
//    }
}
