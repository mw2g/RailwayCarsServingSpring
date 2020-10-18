package com.browarna.railwaycarsserving.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class WagonType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long typeId;
    @Column(nullable = false)
    private String typeName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private WagonGroup wagonGroup;
}
