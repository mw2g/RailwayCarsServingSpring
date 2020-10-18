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
public class IndexToBaseRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long indexId;
    @Column(nullable = false)
    private Date relevanceDate;
    @Column(nullable = false)
    private Double indexToRate;
}
