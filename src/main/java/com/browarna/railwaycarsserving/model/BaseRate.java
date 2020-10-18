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
public class BaseRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rateId;
    @Column(nullable = false)
    private Date relevanceDate;
    @Column(nullable = false)
    private Integer hours;
    @Column(nullable = false)
    private Double rate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private WagonGroup wagonGroup;
}
